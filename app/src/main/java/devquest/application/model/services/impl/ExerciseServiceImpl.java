package devquest.application.model.services.impl;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.ExerciseResponseDTO;
import devquest.application.model.repositories.ExerciseRepository;
import devquest.application.model.services.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService {

  private ExerciseRepository repository;

  public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
    this.repository = exerciseRepository;
  }

  @Override
  public ResponseEntity<ExerciseResponseDTO> generateExercise(Technology technology, Difficulty difficulty) {
    return null;
  }

}
