package devquest.application.model.services;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.ExerciseResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ExerciseService {

  ResponseEntity<ExerciseResponseDTO> generateExercise(Technology technology, Difficulty difficulty);

}
