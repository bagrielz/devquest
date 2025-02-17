package devquest.application.controllers.security;

import devquest.application.model.dtos.security.AccountCredentialsDTO;
import devquest.application.services.security.AuthService;
import devquest.application.services.security.CreateUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private AuthService authService;
  private CreateUserService createUserService;

  public AuthController(AuthService authService,
                        CreateUserService createUserService) {

    this.authService = authService;
    this.createUserService = createUserService;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO data) {
    if (checkIfParamsIsNotNull(data))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

      var token = authService.signin(data);
      if (token == null) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
      }

      return ResponseEntity.ok().body(token);
  }

  @PutMapping("/refresh/{username}")
  public ResponseEntity<?> refreshToken(@PathVariable("username") String username,
                                        @RequestHeader("Authorization") String refreshToken) {

    if (parametersAreInvalid(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("Invalid client request!");
    var token = authService.refreshToken(username, refreshToken);
    if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

    return ResponseEntity.ok(token);
  }

  @PostMapping("/createUser")
  public ResponseEntity<AccountCredentialsDTO> createUser(@RequestBody AccountCredentialsDTO userData) {
    return createUserService.createUser(userData);
  }

  private boolean checkIfParamsIsNotNull(AccountCredentialsDTO data) {
    return data == null || data.getUsername() == null || data.getUsername().isBlank()
            || data.getPassword() == null || data.getPassword().isBlank();
  }

  private boolean parametersAreInvalid(String username, String refreshToken) {
    return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
  }

}
