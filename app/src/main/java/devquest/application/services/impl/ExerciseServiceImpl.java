package devquest.application.services.impl;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.models.dtos.response.exercises.ExerciseResponseDTO;
import devquest.application.services.ExerciseService;
import devquest.application.services.subservices.exercise.AnswerExerciseService;
import devquest.application.services.subservices.exercise.GenerateExerciseService;
import devquest.application.services.subservices.exercise.SearchUnansweredExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService {

  private GenerateExerciseService generateExerciseService;
  private SearchUnansweredExerciseService searchUnansweredExerciseService;
  private AnswerExerciseService answerExerciseService;

  public ExerciseServiceImpl(GenerateExerciseService generateExerciseService,
                             SearchUnansweredExerciseService searchUnansweredExerciseService,
                             AnswerExerciseService answerExerciseService) {

    this.generateExerciseService = generateExerciseService;
    this.searchUnansweredExerciseService = searchUnansweredExerciseService;
    this.answerExerciseService = answerExerciseService;
  }

  @Override
  public ResponseEntity<ExerciseResponseDTO> generateExercise(String token,
                                                              Technology technology,
                                                              Difficulty difficulty) {

    ExerciseResponseDTO unansweredExercise = searchUnansweredExerciseService
            .getUnansweredExercise(token, technology, difficulty);

    if (unansweredExercise != null)
      return new ResponseEntity<>(unansweredExercise, HttpStatus.OK);

    return generateExerciseService.generateExercise(technology, difficulty);
  }

  @Override
  public ResponseEntity<String> answerExercise(String token, Long exerciseId) {
    return answerExerciseService.answerExercise(token, exerciseId);
  }

}
