package devquest.application.services.subservices.exercise;

import devquest.application.exceptions.ExerciseAlreadyAnsweredException;
import devquest.application.exceptions.ResourceNotFoundException;
import devquest.application.model.entities.Exercise;
import devquest.application.model.entities.QuestionsStatistics;
import devquest.application.model.entities.User;
import devquest.application.repositories.ExerciseRepository;
import devquest.application.repositories.UserRepository;
import devquest.application.utilities.TokenJwtDecoder;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnswerExerciseService {

  private ExerciseRepository exerciseRepository;
  private UserRepository userRepository;
  private TokenJwtDecoder tokenJwtDecoder;

  public AnswerExerciseService(ExerciseRepository exerciseRepository,
                               UserRepository userRepository,
                               TokenJwtDecoder tokenJwtDecoder) {

    this.exerciseRepository = exerciseRepository;
    this.userRepository = userRepository;
    this.tokenJwtDecoder = tokenJwtDecoder;
  }

  @Transactional
  public ResponseEntity<String> answerExercise(String token, Long exerciseId) {
    Exercise exercise = getExerciseById(exerciseId);
    User user = getUserByToken(token);
    checkIfThisExerciseHasAnswered(exercise.getId(), user.getId());
    user = updateUserQuestionsStatistics(user);
    registerAnswer(user, exercise);

    return ResponseEntity.ok().body("Exercício respondido com sucesso!");
  }

  private Exercise getExerciseById(Long exerciseId) {
    return exerciseRepository.findById(exerciseId)
            .orElseThrow(() -> new ResourceNotFoundException("Exercise not found!"));
  }

  private User getUserByToken(String token) {
    String username = tokenJwtDecoder.getTokenSubject(token);
    return userRepository.findByUsername(username);
  }

  private void checkIfThisExerciseHasAnswered(Long exerciseId, Long userId) {
    if (!exerciseRepository.userNotAnswerExercise(exerciseId, userId))
      throw new ExerciseAlreadyAnsweredException("This exercise has already answered by this user!");
  }

  private User updateUserQuestionsStatistics(User user) {
    int exercisesCompleted = user.getQuestionsStatistics().getExercisesCompleted();
    user.getQuestionsStatistics().setExercisesCompleted(exercisesCompleted + 1);
    return user;
  }

  private void registerAnswer(User user, Exercise exercise) {
    user.addExercise(exercise);
    exercise.addUser(user);
    userRepository.save(user);
    exerciseRepository.save(exercise);
  }

}
