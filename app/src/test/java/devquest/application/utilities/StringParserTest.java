package devquest.application.utilities;

import devquest.application.config.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StringParserTest {

  @InjectMocks
  private StringParser stringParser;
  private String exercisePrompt;
  private String questionPrompt;
  private String enumeration;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEntities();
  }

  @Test
  void getContentBetweenFlagsWith2Flags() {
    String result = stringParser.getContentBetweenFlags(exercisePrompt, "ENUNCIADO:", "INSTRUÇÕES:");

    assertTrue(result.equals(
            "{Aqui deve ir o enunciado do exercício com no mínimo 2 linhas e no máximo 6 linhas}"));
  }

  @Test
  void getContentBetweenFlagsWith1Flag() {
    String result = stringParser.getContentBetweenFlags(exercisePrompt, "INSTRUÇÕES:", null);

    assertTrue(result.equals(
            "1. {Aqui deve vir a primeira instrução}\n" +
            "2. {Aqui deve vir a segunda instrução}\n" +
            "3. {Aqui deve vir a terceira instrução}"));

    // When just one tag is passed, the method returns all the content from it
  }

  @Test
  void getEnumerationBetweenFlagsWith2Flags() {
    String result = stringParser.getEnumerationBetweenFlags(
            questionPrompt, "ALTERNATIVAS:", "RESPOSTA CORRETA:")
            .toString();

    assertTrue(result.contains("A) {Texto da alternativa A}"));
    assertTrue(result.contains("B) {Texto da alternativa B}"));
    assertTrue(result.contains("C) {Texto da alternativa C}"));
    assertTrue(result.contains("D) {Texto da alternativa D}"));
  }

  @Test
  void getEnumerationBetweenFlagsWith1Flag() {
    String result = stringParser.getEnumerationBetweenFlags(
            exercisePrompt, "INSTRUÇÕES:", null)
            .toString();

    assertTrue(result.contains("1. {Aqui deve vir a primeira instrução}"));
    assertTrue(result.contains("2. {Aqui deve vir a segunda instrução}"));
    assertTrue(result.contains("3. {Aqui deve vir a terceira instrução}"));
  }

  @Test
  void getArrayWithEnumeratorIndicatorAndText() {
    String[] result = stringParser.getArrayWithEnumeratorIndicatorAndText(enumeration, "\\.");

    assertTrue(result[0].equals("1"));
    assertTrue(result[1].contains("{Aqui deve vir a primeira instrução}"));
  }

  public void startEntities() {
    this.exercisePrompt = TestData.exercisePrompt;
    this.questionPrompt = TestData.questionPrompt;
    this.enumeration = TestData.enumeration;
  }

}