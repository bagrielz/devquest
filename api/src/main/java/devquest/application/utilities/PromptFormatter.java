package devquest.application.utilities;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import org.springframework.stereotype.Component;

@Component
public class PromptFormatter {

  private String questionPrompt;
  private String exercisePrompt;

  public PromptFormatter(String questionPrompt, String exercisePrompt) {
    this.exercisePrompt = exercisePrompt;
    this.questionPrompt = questionPrompt;
  }

  private String cloneQuestionPrompt() {
    String clonedPrompt = questionPrompt;
    return clonedPrompt;
  }

  private String cloneExercisePrompt() {
    String clonedPrompt = exercisePrompt;
    return clonedPrompt;
  }

  public String formatQuestionPrompt(Technology technology, Difficulty difficulty) {
    String clonedPrompt = cloneQuestionPrompt();
    return String.format(clonedPrompt, technology, difficulty);
  }

  public String formatExercisePrompt(Technology technology, Difficulty difficulty) {
    String clonedPrompt = cloneExercisePrompt();
    return String.format(clonedPrompt, technology, difficulty);
  }

}
