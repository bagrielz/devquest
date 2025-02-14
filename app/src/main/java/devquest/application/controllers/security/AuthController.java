package devquest.application.controllers.security;

import devquest.application.model.dtos.security.AccountCredentialsDTO;
import devquest.application.services.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacao")
public class AuthController {

  private AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity signin(@RequestBody AccountCredentialsDTO data) {
    if (checkIfParamsIsNotNull(data))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

      var token = authService.signin(data);
      if (token == null) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
      }

      return token;
  }

  private boolean checkIfParamsIsNotNull(AccountCredentialsDTO data) {
    return data == null || data.getUsername() == null || data.getUsername().isBlank()
            || data.getPassword() == null || data.getPassword().isBlank();
  }

}
