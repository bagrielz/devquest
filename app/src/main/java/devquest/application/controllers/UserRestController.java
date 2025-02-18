package devquest.application.controllers;

import devquest.application.model.dtos.response.user.UserInfoDTO;
import devquest.application.services.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UserRestController {

  private UserServiceImpl service;

  public UserRestController(UserServiceImpl userService) {
    this.service = userService;
  }

  @GetMapping("/informacoes")
  public ResponseEntity<UserInfoDTO> getUserInfo(@RequestHeader("Authorization") String token) {
    return service.getUserInfo(token);
  }

}
