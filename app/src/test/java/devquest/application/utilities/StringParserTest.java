package devquest.application.utilities;

import devquest.application.config.TestData;
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
class StringParserTest {

  @InjectMocks
  private StringParser stringParser;
  private String text;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEntities();
  }

  @Test
  void getContentBetweenFlagsWith2Flags() {
    String result = stringParser.getContentBetweenFlags(text, "ENUNCIADO:", "INSTRUÇÕES:");

    assertTrue(result.equals(
            "{Aqui deve ir o enunciado do exercício com no mínimo 2 linhas e no máximo 6 linhas}"));
  }

  @Test
  void getContentBetweenFlagsWith1Flag() {
    String result = stringParser.getContentBetweenFlags(text, "INSTRUÇÕES:", null);

    assertTrue(result.equals(
            "1. {Aqui deve vir a primeira instrução}\n" +
            "2. {Aqui deve vir a segunda instrução}\n" +
            "3. {Aqui deve vir a terceira instrução}"));

    // When just one tag is passed, the method returns all the content from it
  }

  @Test
  void getEnumerationBetweenFlags() {
  }

  @Test
  void getArrayWithEnumeratorIndicatorAndText() {
  }

  public void startEntities() {
    this.text = TestData.exercisePrompt;
  }

}