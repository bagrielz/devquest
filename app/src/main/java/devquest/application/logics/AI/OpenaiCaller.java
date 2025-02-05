package devquest.application.logics.AI;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class OpenaiCaller {

  private ChatClient chatClient;

  public OpenaiCaller(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  public String callOpenai(String prompt) {
    return chatClient
            .prompt()
            .user(prompt)
            .call()
            .content();
  }

}
