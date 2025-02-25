package devquest.application.services.subservices.exercise;

import devquest.application.repositories.ExerciseRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnswerExerciseService {

  private ExerciseRepository exerciseRepository;

  public AnswerExerciseService(ExerciseRepository exerciseRepository) {
    this.exerciseRepository = exerciseRepository;
  }

  @Transactional
  public ResponseEntity<String> answerExercise(String token, Long exerciseId) {
    return null;
  }

}
