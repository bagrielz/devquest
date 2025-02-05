package devquest.application.utilities;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class StringParser {

  public String getContentBetweenFlags(String text, String flag1, String flag2) {
    int start = text.indexOf(flag1) + flag1.length();
    int end;

    if (!flag2.isBlank()) {
      end = text.indexOf(flag2);
      return text.substring(start, end).trim();
    }

    return text.substring(start).trim();
  }

  public Set<String> getEnumerationBetweenFlags(String text, String flag1, String flag2) {
    String enumerationString = "";
    int start = text.indexOf(flag1) + flag1.length();
    int end;

    if (flag2 != null) {
      end = text.indexOf(flag2);
      enumerationString = text.substring(start, end).trim();
    } else {
      enumerationString = text.substring(start).trim();
    }

    return new HashSet<>(Arrays.asList(enumerationString.split("\\n")));
  }

  public String[] getArrayWithEnumeratorIndicatorAndText(String enumerator, String flag) {
    return enumerator.split(flag, 2);
  }

}
