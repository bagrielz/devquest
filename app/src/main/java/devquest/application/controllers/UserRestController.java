package devquest.application.controllers;

import devquest.application.models.dtos.response.user.UserInfoDTO;
import devquest.application.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "User Controller", description = "Controller com endpoints de gerenciamento de usuários")
public class UserRestController {

  private UserService service;

  public UserRestController(UserService userService) {
    this.service = userService;
  }

  @Operation(summary = "Buscar informações gerais do usuário", description = "Puxar informações como id, " +
          "nome completo e questões e exercícios completos",
    tags = {"Buscar"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = UserInfoDTO.class)
          )
        }
      ),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Forbiden", responseCode = "403", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
  @GetMapping("/informacoes")
  public ResponseEntity<UserInfoDTO> getUserInfo(@RequestHeader("Authorization") String token) {
    return service.getUserInfo(token);
  }

}
