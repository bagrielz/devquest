package devquest.application.services.subservices.question;

import devquest.application.dozermapper.DozerMapper;
import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.utilities.OpenaiCaller;
import devquest.application.model.dtos.response.questions.QuestionResponseDTO;
import devquest.application.model.entities.Question;
import devquest.application.model.entities.QuestionOption;
import devquest.application.repositories.QuestionOptionRepository;
import devquest.application.repositories.QuestionRepository;
import devquest.application.utilities.PromptFormatter;
import devquest.application.utilities.StringParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class GenerateQuestionService {

  private QuestionRepository repository;
  private PromptFormatter promptFormatter;
  private OpenaiCaller openaiCaller;
  private StringParser stringParser;
  private QuestionOptionRepository questionOptionRepository;

  public GenerateQuestionService(QuestionRepository repository,
                                 PromptFormatter promptFormatter,
                                 OpenaiCaller openaiCaller,
                                 StringParser stringParser,
                                 QuestionOptionRepository questionOptionRepository) {

    this.repository = repository;
    this.promptFormatter = promptFormatter;
    this.openaiCaller = openaiCaller;
    this.stringParser = stringParser;
    this.questionOptionRepository = questionOptionRepository;
  }

  public ResponseEntity<QuestionResponseDTO> generateQuestion(Technology technology, Difficulty difficulty) {
    String formatedPrompt = promptFormatter.formatQuestionPrompt(technology, difficulty);
    String questionString = openaiCaller.callOpenai(formatedPrompt);
    Question question = createAndSaveQuestion(questionString, technology, difficulty);
    Set<QuestionOption> questionOptions = saveOptionsInDatabase(questionString, question);
    question.setOptions(questionOptions);
    QuestionResponseDTO questionResponseDTO = convertQuestionInQuestionResponseDTO(question);

    return new ResponseEntity<>(questionResponseDTO, HttpStatus.OK);
  }

  private Question createAndSaveQuestion(String questionString, Technology technology, Difficulty difficulty) {
    Question question = Question.builder()
            .technology(technology)
            .difficulty(difficulty)
            .text(stringParser.getContentBetweenFlags(
                    questionString, "ENUNCIADO:", "ALTERNATIVAS:"))
            .correctAnswer(stringParser.getContentBetweenFlags(
                    questionString, "RESPOSTA CORRETA:", "JUSTIFICATIVA:"))
            .justification(stringParser.getContentBetweenFlags(
                    questionString, "JUSTIFICATIVA:", null))
            .createdAt(new Date())
            .build();

    return repository.save(question);
  }

  private Set<QuestionOption> saveOptionsInDatabase(String questionString, Question question) {
    Set<String> optionsString = stringParser.getEnumerationBetweenFlags(
            questionString, "ALTERNATIVAS:", "RESPOSTA CORRETA:");
    Set<QuestionOption> questionOptions = new HashSet<>();
    optionsString.stream().forEach(o -> {
      String[] parts = stringParser.getArrayWithEnumeratorIndicatorAndText(o, "\\)");
      QuestionOption questionOption = createAndSaveOption(parts, question);
      questionOptions.add(questionOption);
    });

    return questionOptions;
  }

  private QuestionOption createAndSaveOption(String[] parts, Question question) {
    QuestionOption questionOption = QuestionOption.builder()
            .optionIndicator(parts[0])
            .optionText(parts[1])
            .question(question)
            .build();

    return questionOptionRepository.save(questionOption);
  }

  private QuestionResponseDTO convertQuestionInQuestionResponseDTO(Question question) {
    return DozerMapper.parseObject(question, QuestionResponseDTO.class);
  }

}
