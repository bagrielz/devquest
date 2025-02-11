package devquest.application.utilities;

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
    questionPrompt =
            "Você é um gerador de questões sobre %s para o nível %s. Crie uma questão no formato abaixo:\n" +
                    "\n" +
                    "ENUNCIADO:\n" +
                    "{Aqui deve ir o enunciado da questão com no mínimo 2 linhas e no máximo 5 linhas}\n" +
                    "\n" +
                    "ALTERNATIVAS:\n" +
                    "A) {Texto da alternativa A}\n" +
                    "B) {Texto da alternativa B}\n" +
                    "C) {Texto da alternativa C}\n" +
                    "D) {Texto da alternativa D}\n" +
                    "\n" +
                    "RESPOSTA CORRETA:\n" +
                    "{Letra da resposta correta (exemplo: A, B, C, D)}\n" +
                    "\n" +
                    "JUSTIFICATIVA:\n" +
                    "Letra da resposta correta e a justificativa dela ser a correta}";

    exercisePrompt =
            "Você é um gerador de exercícios sobre %s para o nível %s. Crie um exercício no formato abaixo:\n" +
                    "\n" +
                    "ENUNCIADO:\n" +
                    "{Aqui deve ir o enunciado do exercício com no mínimo 2 linhas e no máximo 6 linhas}\n" +
                    "\n" +
                    "INSTRUÇÕES:\n" +
                    "1. {Aqui deve vir a primeira instrução}\n" +
                    "2. {Aqui deve vir a segunda instrução}\n" +
                    "3. {Aqui deve vir a terceira instrução}";

    promptFormatter = new PromptFormatter(questionPrompt, exercisePrompt);
  }

}