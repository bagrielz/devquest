package devquest.application.services.subservices.question;

import devquest.application.enums.Status;
import devquest.application.exceptions.QuestionAlreadyAnswered;
import devquest.application.exceptions.QuestionNotFoundException;
import devquest.application.model.dtos.request.AnswerQuestionRequestDTO;
import devquest.application.model.entities.Question;
import devquest.application.model.entities.QuestionsStatistics;
import devquest.application.model.entities.User;
import devquest.application.model.entities.UserQuestion;
import devquest.application.repositories.QuestionRepository;
import devquest.application.repositories.QuestionStatisticsRepository;
import devquest.application.repositories.UserQuestionRepository;
import devquest.application.repositories.UserRepository;
import devquest.application.utilities.TokenJwtDecoder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

  public ResponseEntity<String> answerQuestion(String token, AnswerQuestionRequestDTO answerQuestionRequestDTO) {
    var username = tokenJwtDecoder.getTokenSubject(token);
    var questionID = answerQuestionRequestDTO.getQuestionID();
    var status = answerQuestionRequestDTO.getStatus();

    User user = userRepository.findByUsername(username);
    Question question = getQuestionById(questionID);
    checkIfThisQuestionHasAnswered(question, user);
    createAndSaveUserQuestion(user, question, status);
    updateUserQuestionStatistics(user, status);

    return ResponseEntity.ok().body("Questão respondida com sucesso!");
  }

  private Question getQuestionById(Long id) {
    return questionRepository.findById(id)
            .orElseThrow(() -> new QuestionNotFoundException("Question not found!"));
  }

  private void checkIfThisQuestionHasAnswered(Question question, User user) {
    Optional<UserQuestion> userQuestionOptional = userQuestionRepository.findByQuestionAndUserId(question, user);
    if (userQuestionOptional.isPresent()) throw new QuestionAlreadyAnswered(
            "This question has already answered by this user!");
  }

  private void createAndSaveUserQuestion(User user, Question question, Status status) {
    UserQuestion userQuestion = UserQuestion.builder()
            .user(user)
            .question(question)
            .status(status)
            .build();

    userQuestionRepository.save(userQuestion);
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
