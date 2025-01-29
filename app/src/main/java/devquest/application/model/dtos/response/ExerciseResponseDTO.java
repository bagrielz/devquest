package devquest.application.model.dtos.response;

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
public class ExerciseResponseDTO {

  private Long id;
  private Technology technology;
  private String content;
  private Set<ExerciseInstructionResponseDTO> instructions;

}
