package devquest.application.model.services.impl;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.exercises.ExerciseResponseDTO;
import devquest.application.model.services.ExerciseService;
import devquest.application.model.services.subservices.exercise.GenerateExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService {

  private GenerateExerciseService generateExerciseService;

  public ExerciseServiceImpl(GenerateExerciseService generateExerciseService) {
    this.generateExerciseService = generateExerciseService;
  }

  @Override
  public ResponseEntity<ExerciseResponseDTO> generateExercise(Technology technology, Difficulty difficulty) {
    return generateExerciseService.generateExercise(technology, difficulty);
  }

}
