package devquest.application.utilities;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class QuestionParser {

  public String getText(String questionString) {
    int start = questionString.indexOf("ENUNCIADO:") + "ENUNCIADO:".length();
    int end = questionString.indexOf("ALTERNATIVAS:");
    return questionString.substring(start, end).trim();
  }

  public String getCorrectAnswer(String questionString) {
    int start = questionString.indexOf("RESPOSTA CORRETA:") + "RESPOSTA CORRETA:".length();
    int end = questionString.indexOf("JUSTIFICATIVA:");
    return questionString.substring(start, end).trim();
  }

  public String getJustification(String questionString) {
    int start = questionString.indexOf("JUSTIFICATIVA:") + "JUSTIFICATIVA:".length();
    return questionString.substring(start).trim();
  }

  public Set<String> getOptions(String questionString) {
    int start = questionString.indexOf("ALTERNATIVAS:") + "ALTERNATIVAS:".length();
    int end = questionString.indexOf("RESPOSTA CORRETA:");
    String optionsText = questionString.substring(start, end).trim();
    return new HashSet<>(Arrays.asList(optionsText.split("\\n")));
  }

  public String[] getArrayWithOptionIndicatorAndText(String option) {
    return option.split("\\)", 2);
  }

}
