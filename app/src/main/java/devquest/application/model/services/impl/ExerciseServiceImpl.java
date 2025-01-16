package devquest.application.model.services.impl;

import devquest.application.model.repositories.ExerciseRepository;
import devquest.application.model.services.ExerciseService;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService {

  private ExerciseRepository repository;

  public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
    this.repository = exerciseRepository;
  }

}
