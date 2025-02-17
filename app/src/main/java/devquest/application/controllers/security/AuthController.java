package devquest.application.controllers.security;

import devquest.application.exceptions.ExceptionResponse;
import devquest.application.model.dtos.security.AccountCredentialsDTO;
import devquest.application.model.dtos.security.TokenDTO;
import devquest.application.services.security.AuthService;
import devquest.application.services.security.CreateUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "Controller com endpoints para login e cadastro de usuários")
public class AuthController {

  private AuthService authService;
  private CreateUserService createUserService;

  public AuthController(AuthService authService,
                        CreateUserService createUserService) {

    this.authService = authService;
    this.createUserService = createUserService;
  }

  @Operation(summary = "Logar usuário",
    description = "Logar usuário que já possui cadastro no aplicativo e retornar token JWT. " +
            "O endpoint recebe username e senha",
    tags = {"Segurança"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = TokenDTO.class)
          )
        }
      ),
      @ApiResponse(description = "Credenciais nulas ou inválidas. A API retorna uma String com a mensagem " +
              "'Invalid client request'",
        responseCode = "403",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = String.class)
          )
        }
      ),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
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

  @Operation(summary = "Atualizar token do usuário",
    description = "Atualizar o token do usuário que já está logado no aplicativo. Esse endpoint reccebe o " +
            "username e o refreshToken.",
    tags = {"Segurança"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = TokenDTO.class)
          )
        }
      ),
      @ApiResponse(description = "Requisição passando username ou refreshToken nulos ou refreshToken inválido. " +
              "A API retorna uma String com a mensagem 'Invalid client request!'.",
        responseCode = "403",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = String.class)
          )
        }
      )
    }
  )
  @PutMapping("/refresh/{username}")
  public ResponseEntity<?> refreshToken(@PathVariable("username") String username,
                                        @RequestHeader("Authorization") String refreshToken) {

    if (parametersAreInvalid(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("Invalid client request!");
    var token = authService.refreshToken(username, refreshToken);
    if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

    return ResponseEntity.ok(token);
  }

  @Operation(summary = "Criar um novo usuário",
    description = "Criar um novo usuário comum no banco de dados. " +
            "O endpoint recebe username, password e fullname",
    tags = {"Segurança"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "201",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = AccountCredentialsDTO.class)
          )
        }
      ),
      @ApiResponse(description = "AccountCredentialsDTO da requisição possui atributos nulos. A API retorna um " +
              "ExceptionResponse com a description 'Invalid user data!'.",
        responseCode = "400",
          content = {
            @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ExceptionResponse.class)
            )
          }
      )
    }
  )
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
