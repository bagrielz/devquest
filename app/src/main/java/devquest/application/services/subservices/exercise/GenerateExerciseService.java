package devquest.application.services.subservices.exercise;

import devquest.application.dozermapper.DozerMapper;
import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.exercises.ExerciseResponseDTO;
import devquest.application.model.entities.Exercise;
import devquest.application.model.entities.ExerciseInstruction;
import devquest.application.repositories.ExerciseInstructionRepository;
import devquest.application.repositories.ExerciseRepository;
import devquest.application.utilities.OpenaiCaller;
import devquest.application.utilities.PromptFormatter;
import devquest.application.utilities.StringParser;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class GenerateExerciseService {

  private ExerciseRepository repository;
  private PromptFormatter promptFormatter;
  private OpenaiCaller openaiCaller;
  private StringParser stringParser;
  private ExerciseInstructionRepository exerciseInstructionRepository;

  public GenerateExerciseService(ExerciseRepository repository,
                                 PromptFormatter promptFormatter,
                                 OpenaiCaller openaiCaller,
                                 StringParser stringParser,
                                 ExerciseInstructionRepository exerciseInstructionRepository) {

    this.repository = repository;
    this.promptFormatter = promptFormatter;
    this.openaiCaller = openaiCaller;
    this.stringParser = stringParser;
    this.exerciseInstructionRepository = exerciseInstructionRepository;
  }

  @Transactional
  public ResponseEntity<ExerciseResponseDTO> generateExercise(Technology technology, Difficulty difficulty) {
    String formatedPrompt = promptFormatter.formatExercisePrompt(technology, difficulty);
    String exerciseString = openaiCaller.callOpenai(formatedPrompt);
    Exercise exercise = createAndSaveExercise(exerciseString, technology, difficulty);
    Set<ExerciseInstruction> exerciseInstructions = createAndSaveInstructions(exerciseString, exercise);
    exercise.setInstructions(exerciseInstructions);
    ExerciseResponseDTO exerciseResponseDTO = convertExerciseInExerciseResponseDTO(exercise);

    return new ResponseEntity<>(exerciseResponseDTO, HttpStatus.OK);
  }

  private Exercise createAndSaveExercise(String exerciseString, Technology technology, Difficulty difficulty) {
    Exercise exercise = Exercise.builder()
            .technology(technology)
            .difficulty(difficulty)
            .content(stringParser.getContentBetweenFlags(exerciseString, "ENUNCIADO:", "INSTRUÇÕES:"))
            .createdAt(new Date())
            .build();

    return repository.save(exercise);
  }

  private Set<ExerciseInstruction> createAndSaveInstructions(String exerciseString, Exercise exercise) {
    Set<String> instructionsString = stringParser.getEnumerationBetweenFlags(exerciseString,
            "INSTRUÇÕES:", null);
    Set<ExerciseInstruction> instructions = new HashSet<>();
    instructionsString.forEach(i -> {
      String[] parts = stringParser.getArrayWithEnumeratorIndicatorAndText(i, "\\.");
      ExerciseInstruction exerciseInstruction = createAndSaveExerciseInstruction(parts, exercise);
      instructions.add(exerciseInstruction);
    });

    return instructions;
  }

  private ExerciseInstruction createAndSaveExerciseInstruction(String[] parts, Exercise exercise) {
    ExerciseInstruction exerciseInstruction = ExerciseInstruction.builder()
            .indicator(parts[0])
            .text(parts[1])
            .exercise(exercise)
            .build();

    return exerciseInstructionRepository.save(exerciseInstruction);
  }

  private ExerciseResponseDTO convertExerciseInExerciseResponseDTO(Exercise exercise) {
    return DozerMapper.parseObject(exercise, ExerciseResponseDTO.class);
  }

}
