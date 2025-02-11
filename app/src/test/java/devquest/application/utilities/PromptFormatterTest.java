package devquest.application.utilities;

import devquest.application.config.TestData;
import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PromptFormatterTest {

  @InjectMocks
  private PromptFormatter promptFormatter;

  private String questionPrompt;
  private String exercisePrompt;
  private Technology technology = Technology.JAVA;
  private Difficulty difficulty = Difficulty.AVANCADO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEntities();
  }

  @Test
  void formatQuestionPrompt() {
    String result = promptFormatter.formatQuestionPrompt(technology, difficulty);

    assertTrue(result.contains("JAVA"));
    assertTrue(result.contains("AVANCADO"));
  }

  @Test
  void formatExercisePrompt() {
    String result = promptFormatter.formatExercisePrompt(technology, difficulty);

    assertTrue(result.contains("JAVA"));
    assertTrue(result.contains("AVANCADO"));
  }

  public void startEntities() {
    questionPrompt = TestData.questionPrompt;
    exercisePrompt = TestData.exercisePrompt;
    promptFormatter = new PromptFormatter(questionPrompt, exercisePrompt);
  }

}