package devquest.application.services;

import devquest.application.model.dtos.response.user.UserInfoDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<UserInfoDTO> getUserInfo(String token);

}
