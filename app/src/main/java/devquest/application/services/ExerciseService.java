package devquest.application.services;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.exercises.ExerciseResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ExerciseService {

  ResponseEntity<ExerciseResponseDTO> generateExercise(String token, Technology technology, Difficulty difficulty);
  ResponseEntity<String> answerExercise(String token, Long exerciseId);

}
