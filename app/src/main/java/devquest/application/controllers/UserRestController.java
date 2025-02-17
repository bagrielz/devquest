package devquest.application.controllers;

import devquest.application.services.impl.UserProfileProfileServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UserRestController {

  private UserProfileProfileServiceImpl service;

  public UserRestController(UserProfileProfileServiceImpl userService) {
    this.service = userService;
  }

}
