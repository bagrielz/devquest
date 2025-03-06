package devquest.application.services;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.models.dtos.response.exercises.ExerciseResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IExerciseService {

  ResponseEntity<ExerciseResponseDTO> generateExercise(String token, Technology technology, Difficulty difficulty);
  ResponseEntity<String> answerExercise(String token, Long exerciseId);

}
