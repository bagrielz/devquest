package devquest.application.controllers;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.exercises.ExerciseResponseDTO;
import devquest.application.services.impl.ExerciseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/exercicios")
@Tag(name = "Exercise Controller", description = "Controller com endpoints de gerenciamento de exercícios")
public class ExerciseRestController {

  private ExerciseServiceImpl service;

  public ExerciseRestController(ExerciseServiceImpl exerciseService) {
    this.service = exerciseService;
  }

  @Operation(summary = "Gerar novo exercício",
    description = "Gerar um novo exercício para a tecnologia e dificuldade que o usuário passar " +
            "no parâmetro da requisição",
    tags = {"Gerar"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ExerciseResponseDTO.class)
          )
        }
      ),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Forbiden", responseCode = "403", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
  @GetMapping("/gerar")
  public ResponseEntity<ExerciseResponseDTO> generateExercise(
          @RequestParam(name = "tecnologia") Technology technology,
          @RequestParam(name = "dificuldade") Difficulty difficulty
          ) {
    return service.generateExercise(technology, difficulty);
  }

}
