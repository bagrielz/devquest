package devquest.application.model.dtos.response;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponseDTO {

  private Long id;
  private Technology technology;
  private Difficulty difficulty;
  private String text;
  private String correctAnswer;
  private String justification;
  private Set<QuestionOptionResponseDTO> options;

}
