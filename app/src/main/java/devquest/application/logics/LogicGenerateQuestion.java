package devquest.application.logics;

import devquest.application.dozermapper.DozerMapper;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.QuestionResponseDTO;
import devquest.application.model.entities.Option;
import devquest.application.model.entities.Question;
import devquest.application.model.repositories.OptionRepository;
import devquest.application.model.repositories.QuestionRepository;
import devquest.application.utilities.QuestionParser;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class LogicGenerateQuestion {

  private String prompt;
  private ChatClient chatClient;
  private QuestionParser questionParser;
  private QuestionRepository questionRepository;
  private OptionRepository optionRepository;

  public LogicGenerateQuestion(String prompt,
                               ChatClient.Builder chatClientBuilder,
                               QuestionParser questionParser,
                               QuestionRepository questionRepository,
                               OptionRepository optionRepository) {
    this.prompt = prompt;
    this.chatClient = chatClientBuilder.build();
    this.questionParser = questionParser;
    this.questionRepository = questionRepository;
    this.optionRepository = optionRepository;
  }

  public String formatprompt(Technology technology) {
    String promptCloned = prompt;
    return String.format(promptCloned, technology.getTechnology());
  }

  public String callOpenaiAndReturnResponse(String formattedPrompt) {
    return chatClient
            .prompt()
            .user(formattedPrompt)
            .call()
            .content();
  }

  public Question createQuestionObjectAndSaveInDatabase(String questionString, Technology technology) {
    Question question = Question.builder()
            .technology(technology)
            .text(questionParser.getText(questionString))
            .correctAnswer(questionParser.getCorrectAnswer(questionString))
            .justification(questionParser.getJustification(questionString))
            .createdAt(new Date())
            .build();

    return questionRepository.save(question);
  }

  public Set<Option> saveOptionsInDatabaseAndReturnAnOptionSet(String questionString, Question question) {
    Set<String> optionsString = questionParser.getOptions(questionString);
    Set<Option> options = new HashSet<>();
    optionsString.stream().forEach(o -> {
      String[] parts = questionParser.getArrayWithOptionIndicatorAndText(o);
      Option option = createOptionObject(parts, question);
      options.add(optionRepository.save(option));
    });

    return options;
  }

  private Option createOptionObject(String[] parts, Question question) {
    return Option.builder()
            .optionIndicator(parts[0])
            .optionText(parts[1])
            .question(question)
            .build();
  }

  public QuestionResponseDTO parseQuestionToQuestionResponseDTO(Question question) {
    return DozerMapper.parseObject(question, QuestionResponseDTO.class);
  }

}
