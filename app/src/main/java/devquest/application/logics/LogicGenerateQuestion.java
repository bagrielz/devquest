package devquest.application.logics;

import devquest.application.dozermapper.DozerMapper;
import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.QuestionResponseDTO;
import devquest.application.model.entities.QuestionOption;
import devquest.application.model.entities.Question;
import devquest.application.model.repositories.QuestionOptionRepository;
import devquest.application.model.repositories.QuestionRepository;
import devquest.application.utilities.StringParser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class LogicGenerateQuestion {

  private String prompt;
  private StringParser stringParser;
  private QuestionRepository questionRepository;
  private QuestionOptionRepository questionOptionRepository;

  public LogicGenerateQuestion(String prompt,
                               StringParser stringParser,
                               QuestionRepository questionRepository,
                               QuestionOptionRepository questionOptionRepository) {
    this.prompt = prompt;
    this.stringParser = stringParser;
    this.questionRepository = questionRepository;
    this.questionOptionRepository = questionOptionRepository;
  }

  public String cloneAndFormatPrompt(Technology technology, Difficulty difficulty) {
    String promptCloned = prompt;
    return String.format(promptCloned, technology, difficulty);
  }

  public Question createAndSaveQuestion(String questionString, Technology technology, Difficulty difficulty) {
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

    return questionRepository.save(question);
  }

  public Set<QuestionOption> saveOptionsInDatabase(String questionString, Question question) {
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

  public QuestionResponseDTO parseQuestionToQuestionResponseDTO(Question question) {
    return DozerMapper.parseObject(question, QuestionResponseDTO.class);
  }

}
