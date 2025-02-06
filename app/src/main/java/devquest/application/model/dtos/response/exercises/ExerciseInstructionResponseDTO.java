package devquest.application.model.dtos.response.exercises;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseInstructionResponseDTO {

  private Long id;
  private String indicator;
  private String text;

}
