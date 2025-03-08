package devquest.application.models.dtos.response.exercises;

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
public class ExerciseResponseDTO {

  private Long id;
  private Technology technology;
  private Difficulty difficulty;
  private String content;
  private Set<ExerciseInstructionResponseDTO> instructions;

}
