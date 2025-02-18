package devquest.application.services.subservices.question;

import devquest.application.model.dtos.request.AnswerQuestionRequestDTO;
import devquest.application.repositories.QuestionRepository;
import devquest.application.repositories.UserQuestionRepository;
import devquest.application.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnswerQuestionService {

  private QuestionRepository questionRepository;
  private UserRepository userRepository;
  private UserQuestionRepository userQuestionRepository;

  public AnswerQuestionService(QuestionRepository questionRepository,
                               UserRepository userRepository,
                               UserQuestionRepository userQuestionRepository) {

    this.questionRepository = questionRepository;
    this.userRepository = userRepository;
    this.userQuestionRepository = userQuestionRepository;
  }

  public ResponseEntity<?> answerQuestion(String token, AnswerQuestionRequestDTO answerQuestionRequestDTO) {
    return null;
  }

}
