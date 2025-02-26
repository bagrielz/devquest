package devquest.application.services;

import devquest.application.models.dtos.response.user.UserInfoDTO;
import org.springframework.http.ResponseEntity;

public interface IUserService {

  ResponseEntity<UserInfoDTO> getUserInfo(String token);

}
