package devquest.application.services.subservices.question;

import devquest.application.enums.Status;
import devquest.application.exceptions.QuestionAlreadyAnsweredException;
import devquest.application.exceptions.ResourceNotFoundException;
import devquest.application.models.dtos.request.AnswerQuestionRequestDTO;
import devquest.application.models.entities.Question;
import devquest.application.models.entities.QuestionsStatistics;
import devquest.application.models.entities.User;
import devquest.application.models.entities.UserQuestion;
import devquest.application.repositories.QuestionRepository;
import devquest.application.repositories.QuestionStatisticsRepository;
import devquest.application.repositories.UserQuestionRepository;
import devquest.application.repositories.UserRepository;
import devquest.application.utilities.TokenJwtDecoder;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AnswerQuestionService {

  private QuestionRepository questionRepository;
  private UserRepository userRepository;
  private UserQuestionRepository userQuestionRepository;
  private QuestionStatisticsRepository questionStatisticsRepository;
  private TokenJwtDecoder tokenJwtDecoder;

  public AnswerQuestionService(QuestionRepository questionRepository,
                               UserRepository userRepository,
                               UserQuestionRepository userQuestionRepository,
                               QuestionStatisticsRepository questionStatisticsRepository,
                               TokenJwtDecoder tokenJwtDecoder) {

    this.questionRepository = questionRepository;
    this.userRepository = userRepository;
    this.userQuestionRepository = userQuestionRepository;
    this.questionStatisticsRepository = questionStatisticsRepository;
    this.tokenJwtDecoder = tokenJwtDecoder;
  }

  @Transactional
  public ResponseEntity<String> answerQuestion(String token, AnswerQuestionRequestDTO answerQuestionRequestDTO) {
    User user = getUserByToken(token);
    Question question = getQuestionById(answerQuestionRequestDTO.getQuestionID());
    checkIfThisQuestionHasAnswered(question, user);
    UserQuestion userQuestion = createAndSaveUserQuestion(user, question, answerQuestionRequestDTO.getStatus());
    relateUserQuestionInUserAndQuestion(user, question, userQuestion);
    updateUserQuestionStatistics(user, answerQuestionRequestDTO.getStatus());

    return ResponseEntity.ok().body("Questão respondida com sucesso!");
  }

  private User getUserByToken(String token) {
    String username = tokenJwtDecoder.getTokenSubject(token);
    return userRepository.findByUsername(username);
  }

  private Question getQuestionById(Long id) {
    return questionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Question not found!"));
  }

  private void checkIfThisQuestionHasAnswered(Question question, User user) {
    if (userQuestionRepository.findByQuestionAndUserId(question, user).isPresent())
      throw new QuestionAlreadyAnsweredException("This question has already answered by this user!");
  }

  private UserQuestion createAndSaveUserQuestion(User user, Question question, Status status) {
    UserQuestion userQuestion = UserQuestion.builder()
            .user(user)
            .question(question)
            .status(status)
            .build();

    return userQuestionRepository.save(userQuestion);
  }

  private void relateUserQuestionInUserAndQuestion(User user, Question question, UserQuestion userQuestion) {
    Set<UserQuestion> userUserQuestions = user.getUserQuestions();
    Set<UserQuestion> questionUserQuestions = question.getUserQuestions();
    userUserQuestions.add(userQuestion);
    questionUserQuestions.add(userQuestion);

    user.setUserQuestions(userUserQuestions);
    question.setUserQuestions(questionUserQuestions);
  }

  private void updateUserQuestionStatistics(User user, Status status) {
    if (status.getStatus().equalsIgnoreCase("CORRETO")) {
      QuestionsStatistics userQuestionStatistics = user.getQuestionsStatistics();
      increase1ToCorrectQuestionsAndSaveQuestionStatistics(userQuestionStatistics);
    }
  }

  private void increase1ToCorrectQuestionsAndSaveQuestionStatistics(
          QuestionsStatistics userQuestionStatistics) {

    userQuestionStatistics.setCorrectQuestions(userQuestionStatistics.getCorrectQuestions() + 1);
    questionStatisticsRepository.save(userQuestionStatistics);
  }

}
