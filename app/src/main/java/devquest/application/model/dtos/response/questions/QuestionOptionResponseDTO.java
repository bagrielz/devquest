package devquest.application.model.dtos.response.questions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionOptionResponseDTO {

  private Long id;
  private String optionIndicator;
  private String optionText;

}
