package devquest.application.services.subservices.exercise;

import devquest.application.dozermapper.DozerMapper;
import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.models.dtos.response.exercises.ExerciseResponseDTO;
import devquest.application.models.entities.Exercise;
import devquest.application.models.entities.User;
import devquest.application.repositories.ExerciseRepository;
import devquest.application.repositories.UserRepository;
import devquest.application.utilities.TokenJwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchUnansweredExerciseService {

  private ExerciseRepository exerciseRepository;
  private UserRepository userRepository;
  private TokenJwtDecoder tokenJwtDecoder;

  public SearchUnansweredExerciseService(ExerciseRepository exerciseRepository,
                                         UserRepository userRepository,
                                         TokenJwtDecoder tokenJwtDecoder) {

    this.exerciseRepository = exerciseRepository;
    this.userRepository = userRepository;
    this.tokenJwtDecoder = tokenJwtDecoder;
  }

  public ExerciseResponseDTO getUnansweredExercise(String token, Technology technology, Difficulty difficulty) {
    List<Exercise> exercises = getAllExercisesWithThisTechnologyAndDifficulty(technology, difficulty);
    if (checkIfExerciseListIsNull(exercises)) return null;
    User user = decodeTokenAndGetUser(token);
    Exercise unansweredExercise = findForExerciseUnansweredByUser(exercises, user);
    if (unansweredExercise == null) return null;

    return convertToDTO(unansweredExercise);
  }

  private List<Exercise> getAllExercisesWithThisTechnologyAndDifficulty(Technology technology, Difficulty difficulty) {
    return exerciseRepository.findAllByTechnologyAndDifficulty(technology, difficulty);
  }

  private boolean checkIfExerciseListIsNull(List<Exercise> exercises) {
    return exercises.isEmpty();
  }

  private User decodeTokenAndGetUser(String token) {
    String username = tokenJwtDecoder.getTokenSubject(token);
    return userRepository.findByUsername(username);
  }

  private Exercise findForExerciseUnansweredByUser(List<Exercise> exercises, User user) {
    return exercises.stream()
            .filter(exercise -> exerciseRepository.userNotAnswerExercise(exercise.getId(), user.getId()))
            .findFirst()
            .orElse(null);
  }

  private ExerciseResponseDTO convertToDTO(Exercise exercise) {
    return DozerMapper.parseObject(exercise, ExerciseResponseDTO.class);
  }

}
