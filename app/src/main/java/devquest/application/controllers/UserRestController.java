package devquest.application.controllers;

import devquest.application.model.services.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

  private UserServiceImpl service;

  public UserRestController(UserServiceImpl userService) {
    this.service = userService;
  }

}
