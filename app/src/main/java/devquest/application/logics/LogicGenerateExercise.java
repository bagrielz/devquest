package devquest.application.logics;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.entities.Exercise;
import devquest.application.model.entities.ExerciseInstruction;
import devquest.application.model.repositories.ExerciseInstructionRepository;
import devquest.application.model.repositories.ExerciseRepository;
import devquest.application.utilities.StringParser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class LogicGenerateExercise {

  private String exercisePrompt;
  private StringParser stringParser;
  private ExerciseRepository exerciseRepository;
  private ExerciseInstructionRepository exerciseInstructionRepository;

  public LogicGenerateExercise(String exercisePrompt,
                               StringParser stringParser,
                               ExerciseRepository exerciseRepository,
                               ExerciseInstructionRepository exerciseInstructionRepository) {

    this.exercisePrompt = exercisePrompt;
    this.stringParser = stringParser;
    this.exerciseRepository = exerciseRepository;
    this.exerciseInstructionRepository = exerciseInstructionRepository;
  }

  public String cloneAndFormatPrompt(Technology technology, Difficulty difficulty) {
    String clonedPrompt = exercisePrompt;
    return String.format(exercisePrompt, technology, difficulty);
  }

  public Exercise createAndSaveExercise(Technology technology, Difficulty difficulty, String exerciseString) {
    Exercise exercise = Exercise.builder()
            .technology(technology)
            .difficulty(difficulty)
            .content(stringParser.getContentBetweenFlags(exerciseString, "ENUNCIADO:", "INSTRUÇÕES:"))
            .createdAt(new Date())
            .build();

    return exerciseRepository.save(exercise);
  }

  public Set<ExerciseInstruction> saveInstructionsInDatabase(String exerciseString, Exercise exercise) {
    Set<String> instructionsString = stringParser.getEnumerationBetweenFlags(exerciseString,
            "INSTRUÇÕES:", null);
    Set<ExerciseInstruction> instructions = new HashSet<>();
    instructionsString.forEach(i -> {
      String[] instructionIndicatorAndText = stringParser.getArrayWithEnumeratorIndicatorAndText(i, "\\.");
      ExerciseInstruction exerciseInstruction = createAndSaveExerciseInstruction(instructionIndicatorAndText, exercise);
      instructions.add(exerciseInstruction);
    });

    return instructions;
  }

  private ExerciseInstruction createAndSaveExerciseInstruction(String[] indicatorAndText, Exercise exercise) {
    ExerciseInstruction exerciseInstruction = ExerciseInstruction.builder()
            .indicator(indicatorAndText[0])
            .text(indicatorAndText[1])
            .exercise(exercise)
            .build();

    return exerciseInstructionRepository.save(exerciseInstruction);
  }

}
