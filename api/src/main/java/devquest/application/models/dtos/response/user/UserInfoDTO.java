package devquest.application.models.dtos.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {

  private Long userId;
  private String fullName;
  private Integer correctQuestions;
  private Integer exercisesCompleted;

}
