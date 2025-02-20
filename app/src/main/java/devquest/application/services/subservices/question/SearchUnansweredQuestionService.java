package devquest.application.services.subservices.question;

import devquest.application.dozermapper.DozerMapper;
import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.questions.QuestionResponseDTO;
import devquest.application.model.entities.Question;
import devquest.application.model.entities.User;
import devquest.application.repositories.QuestionRepository;
import devquest.application.repositories.UserQuestionRepository;
import devquest.application.repositories.UserRepository;
import devquest.application.utilities.TokenJwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchUnansweredQuestionService {

  private QuestionRepository questionRepository;
  private UserRepository userRepository;
  private UserQuestionRepository userQuestionRepository;
  private TokenJwtDecoder tokenJwtDecoder;

  public SearchUnansweredQuestionService(QuestionRepository questionRepository,
                                         UserRepository userRepository,
                                         UserQuestionRepository userQuestionRepository,
                                         TokenJwtDecoder tokenJwtDecoder) {

    this.questionRepository = questionRepository;
    this.userRepository = userRepository;
    this.userQuestionRepository = userQuestionRepository;
    this.tokenJwtDecoder = tokenJwtDecoder;
  }

  public QuestionResponseDTO getUnansweredQuestion(String token, Technology technology, Difficulty difficulty) {
    List<Question> questions = getAllQuestionsWithThisTechnologyAndDifficulty(technology, difficulty);
    if (checkIfQuestionsListIsNull(questions)) return null;
    User user = decodeTokenAndGetUser(token);
    Question unansweredQuestion = findForQuestionUnansweredByUser(questions, user);
    if (unansweredQuestion == null) return null;
    QuestionResponseDTO questionResponseDTO = DozerMapper.parseObject(unansweredQuestion, QuestionResponseDTO.class);

    return questionResponseDTO;
  }

  private List<Question> getAllQuestionsWithThisTechnologyAndDifficulty(Technology technology, Difficulty difficulty) {
    return questionRepository.findAllByTechnologyAndDifficulty(technology, difficulty);
  }

  private boolean checkIfQuestionsListIsNull(List<Question> questions) {
    return questions.isEmpty();
  }

  private Question findForQuestionUnansweredByUser(List<Question> questions, User user) {
    return questions.stream()
            .filter(question -> userQuestionRepository.findByQuestionAndUserId(question, user).isEmpty())
            .findFirst()
            .orElse(null);
  }

  private User decodeTokenAndGetUser(String token) {
    String username = tokenJwtDecoder.getTokenSubject(token);
    return userRepository.findByUsername(username);
  }

}
