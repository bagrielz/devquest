package devquest.application.model.services.impl;

import devquest.application.config.PromptTemplate;
import devquest.application.model.dtos.responses.QuestionResponseDTO;
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
                             String promptTemplate,
                             ChatClient.Builder chatClientBuilder) {
    this.repository = questionRepository;
    this.prompt = promptTemplate;
    this.chatClient = chatClientBuilder.build();
  }

  private String formatePrompt(String technology) {
    return this.prompt.replace("{technology}", technology);
  }

  @Override
  public ResponseEntity<QuestionResponseDTO> generateQuestion(String technology) {
    String formatedPrompt = this.formatePrompt(technology);
    String response = chatClient
            .prompt()
            .user(formatedPrompt)
            .call()
            .content();
    return null;
  }

}
