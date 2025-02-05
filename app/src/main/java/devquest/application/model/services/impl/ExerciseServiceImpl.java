package devquest.application.model.services.impl;

import devquest.application.dozermapper.DozerMapper;
import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.ExerciseResponseDTO;
import devquest.application.model.entities.Exercise;
import devquest.application.model.entities.ExerciseInstruction;
import devquest.application.model.repositories.ExerciseInstructionRepository;
import devquest.application.model.repositories.ExerciseRepository;
import devquest.application.model.services.ExerciseService;
import devquest.application.utilities.StringParser;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExerciseServiceImpl implements ExerciseService {

  private ExerciseRepository repository;
  private String exercisePrompt;
  private ChatClient chatClient;
  private StringParser stringParser;
  private ExerciseInstructionRepository exerciseInstructionRepository;

  public ExerciseServiceImpl(ExerciseRepository exerciseRepository,
                             String exercisePrompt,
                             ChatClient.Builder chatClientBuilder,
                             StringParser stringParser,
                             ExerciseInstructionRepository exerciseInstructionRepository) {

    this.repository = exerciseRepository;
    this.exercisePrompt = exercisePrompt;
    this.chatClient = chatClientBuilder.build();
    this.stringParser = stringParser;
    this.exerciseInstructionRepository = exerciseInstructionRepository;
  }

  @Override
  public ResponseEntity<ExerciseResponseDTO> generateExercise(Technology technology, Difficulty difficulty) {
    String formattedPrompt = cloneAndFormatPrompt(technology, difficulty);
    String exerciseString = callOpenaiAPIandReturnExercise(formattedPrompt);
    Exercise exercise = Exercise.builder()
            .technology(technology)
            .difficulty(difficulty)
            .content(stringParser.getContentBetweenFlags(exerciseString, "ENUNCIADO:", "INSTRUÇÕES:"))
            .createdAt(new Date())
            .build();
    exercise = repository.save(exercise);
    Set<String> exerciseInstructionsString = stringParser
            .getEnumerationBetweenFlags(exerciseString, "INSTRUÇÕES:", null);
    Set<ExerciseInstruction> exerciseInstructions =
            saveInstructionsInDatabaseAndReturnInstructionsSet(exerciseInstructionsString, exercise);
    exercise.setInstructions(exerciseInstructions);
    ExerciseResponseDTO exerciseResponseDTO = DozerMapper.parseObject(exercise, ExerciseResponseDTO.class);

    return new ResponseEntity<>(exerciseResponseDTO, HttpStatus.OK);
  }

  private String cloneAndFormatPrompt(Technology technology, Difficulty difficulty) {
    String clonedPrompt = exercisePrompt;
    return String.format(exercisePrompt, technology, difficulty);
  }

  private String callOpenaiAPIandReturnExercise(String formattedPrompt) {
    return chatClient
            .prompt()
            .user(formattedPrompt)
            .call()
            .content();
  }

  private Set<ExerciseInstruction> saveInstructionsInDatabaseAndReturnInstructionsSet(Set<String> exerciseInstructions,
                                                                                      Exercise exercise) {
    Set<ExerciseInstruction> instructions = new HashSet<>();
    exerciseInstructions.forEach(i -> {
      String[] instructionIndicatorAndText = stringParser.getArrayWithEnumeratorIndicatorAndText(i, "\\.");
      ExerciseInstruction exerciseInstruction = ExerciseInstruction.builder()
              .indicator(instructionIndicatorAndText[0])
              .text(instructionIndicatorAndText[1])
              .exercise(exercise)
              .build();
      instructions.add(exerciseInstructionRepository.save(exerciseInstruction));
    });

    return instructions;
  }

}
