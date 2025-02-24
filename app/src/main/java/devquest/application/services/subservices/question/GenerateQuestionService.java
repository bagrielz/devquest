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
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
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

  @Transactional
  public ResponseEntity<QuestionResponseDTO> generateQuestion(Technology technology, Difficulty difficulty) {
    String formatedPrompt = promptFormatter.formatQuestionPrompt(technology, difficulty);
    String questionString = openaiCaller.callOpenai(formatedPrompt);
    Question question = createAndSaveQuestion(questionString, technology, difficulty);
    saveOptionsAndRelateWithQuestion(questionString, question);
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

  private void saveOptionsAndRelateWithQuestion(String questionString, Question question) {
    Set<String> optionsString = stringParser.getEnumerationBetweenFlags(
            questionString, "ALTERNATIVAS:", "RESPOSTA CORRETA:");
    optionsString.stream().forEach(o -> {
      String[] parts = stringParser.getArrayWithEnumeratorIndicatorAndText(o, "\\)");
      QuestionOption questionOption = createAndSaveOption(parts, question);
      question.addOption(questionOption);
    });
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
