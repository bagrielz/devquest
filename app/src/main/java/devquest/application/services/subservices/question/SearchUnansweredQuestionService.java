package devquest.application.services.subservices.question;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.questions.QuestionResponseDTO;
import devquest.application.repositories.QuestionRepository;
import devquest.application.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SearchUnansweredQuestionService {

  private QuestionRepository questionRepository;
  private UserRepository userRepository;

  public SearchUnansweredQuestionService(QuestionRepository questionRepository,
                                         UserRepository userRepository) {

    this.questionRepository = questionRepository;
    this.userRepository = userRepository;
  }

  public QuestionResponseDTO getUnansweredQuestion(String token, Technology technology, Difficulty difficulty) {
    return null;
  }

}
