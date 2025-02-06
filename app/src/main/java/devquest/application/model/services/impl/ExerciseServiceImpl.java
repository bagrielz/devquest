package devquest.application.model.services.impl;

import devquest.application.dozermapper.DozerMapper;
import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.utilities.OpenaiCaller;
import devquest.application.logics.LogicGenerateExercise;
import devquest.application.model.dtos.response.ExerciseResponseDTO;
import devquest.application.model.entities.Exercise;
import devquest.application.model.entities.ExerciseInstruction;
import devquest.application.model.repositories.ExerciseRepository;
import devquest.application.model.services.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ExerciseServiceImpl implements ExerciseService {

  private ExerciseRepository repository;
  private LogicGenerateExercise logicGenerateExercise;
  private OpenaiCaller openaiCaller;

  public ExerciseServiceImpl(ExerciseRepository exerciseRepository,
                             LogicGenerateExercise logicGenerateExercise,
                             OpenaiCaller openaiCaller) {

    this.repository = exerciseRepository;
    this.logicGenerateExercise = logicGenerateExercise;
    this.openaiCaller = openaiCaller;
  }

  @Override
  public ResponseEntity<ExerciseResponseDTO> generateExercise(Technology technology, Difficulty difficulty) {
    String formattedPrompt = logicGenerateExercise.cloneAndFormatPrompt(technology, difficulty);
    String exerciseString = openaiCaller.callOpenai(formattedPrompt);
    Exercise exercise = logicGenerateExercise.createAndSaveExercise(technology, difficulty, exerciseString);
    Set<ExerciseInstruction> exerciseInstructions = logicGenerateExercise.saveInstructionsInDatabase(
            exerciseString, exercise);
    exercise.setInstructions(exerciseInstructions);
    ExerciseResponseDTO exerciseResponseDTO = DozerMapper.parseObject(exercise, ExerciseResponseDTO.class);

    return new ResponseEntity<>(exerciseResponseDTO, HttpStatus.OK);
  }

}
