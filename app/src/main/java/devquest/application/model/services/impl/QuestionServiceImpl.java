package devquest.application.model.services.impl;

import devquest.application.dozermapper.DozerMapper;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.QuestionResponseDTO;
import devquest.application.model.entities.Option;
import devquest.application.model.entities.Question;
import devquest.application.model.repositories.OptionRepository;
import devquest.application.model.repositories.QuestionRepository;
import devquest.application.model.services.QuestionService;
import devquest.application.utilities.QuestionParser;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {

  private QuestionRepository repository;
  private String prompt;
  private ChatClient chatClient;
  private QuestionParser questionParser;
  private OptionRepository optionRepository;

  public QuestionServiceImpl(QuestionRepository questionRepository,
                             String prompt,
                             ChatClient.Builder chatClientBuilder,
                             QuestionParser questionParser,
                             OptionRepository optionRepository) {

    this.repository = questionRepository;
    this.prompt = prompt;
    this.chatClient = chatClientBuilder.build();
    this.questionParser = questionParser;
    this.optionRepository = optionRepository;
  }

  @Override
  public ResponseEntity<QuestionResponseDTO> generateQuestion(Technology technology) {
    String formattedPrompt = formatprompt(technology);
    String questionString = callOpenaiAndReturnResponse(formattedPrompt);
    Question question = createQuestionObject(questionString, technology);
    question = repository.save(question);
    Set<Option> options = saveOptionsInDatabaseAndReturnAnOptionSet(questionString, question);
    question.setOptions(options);
    QuestionResponseDTO questionResponseDTO = DozerMapper.parseObject(question, QuestionResponseDTO.class);

    return new ResponseEntity<>(questionResponseDTO, HttpStatus.CREATED);
  }

  private String formatprompt(Technology technology) {
    String promptCloned = prompt;
    return String.format(promptCloned, technology.getTechnology());
  }

  private String callOpenaiAndReturnResponse(String formattedPrompt) {
    return chatClient
            .prompt()
            .user(formattedPrompt)
            .call()
            .content();
  }

  private Question createQuestionObject(String questionString, Technology technology) {
    return Question.builder()
            .technology(technology)
            .text(questionParser.getText(questionString))
            .correctAnswer(questionParser.getCorrectAnswer(questionString))
            .justification(questionParser.getJustification(questionString))
            .createdAt(new Date())
            .build();
  }

  private Set<Option> saveOptionsInDatabaseAndReturnAnOptionSet(String questionString, Question question) {
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

}
