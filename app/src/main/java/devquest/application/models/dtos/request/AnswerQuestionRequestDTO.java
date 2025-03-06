package devquest.application.models.dtos.request;

import devquest.application.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerQuestionRequestDTO {

  private Long questionID;
  private Status status;

}
