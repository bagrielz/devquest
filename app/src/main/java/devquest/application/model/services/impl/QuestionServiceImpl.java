package devquest.application.model.services.impl;

import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.QuestionResponseDTO;
import devquest.application.model.repositories.QuestionRepository;
import devquest.application.model.services.QuestionService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

  private QuestionRepository repository;
  private String prompt;
  private ChatClient chatClient;

  public QuestionServiceImpl(QuestionRepository questionRepository,
                             String prompt,
                             ChatClient.Builder chatClientBuilder) {

    this.repository = questionRepository;
    this.prompt = prompt;
    this.chatClient = chatClientBuilder.build();
  }

  @Override
  public ResponseEntity<QuestionResponseDTO> generateQuestion(Technology technology) {
    String formattedPrompt = formatprompt(technology);
    String questionString = callOpenaiAndReturnResponse(formattedPrompt);
    return null;
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

}
