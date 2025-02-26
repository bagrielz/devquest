package devquest.application.controllers;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.exceptions.responses.ExceptionResponse;
import devquest.application.models.dtos.request.AnswerQuestionRequestDTO;
import devquest.application.models.dtos.response.questions.QuestionResponseDTO;
import devquest.application.services.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/questoes")
@Tag(name = "Question Controller", description = "Controller com endpoints de gerenciamento de questões")
public class QuestionRestController {

  private QuestionService service;

  public QuestionRestController(QuestionService questionService) {
    this.service = questionService;
  }

  @Operation(summary = "Gerar nova questão",
    description = "Gerar uma nova questão para a tecnologia e dificuldade que o usuário passar " +
            "no parâmetro da requisição",
    tags = {"Gerar"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = QuestionResponseDTO.class)
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
  public ResponseEntity<QuestionResponseDTO> generateQuestion(
          @RequestHeader("Authorization") String token,
          @RequestParam(name = "tecnologia") Technology technology,
          @RequestParam(name = "dificuldade") Difficulty difficulty
          ) {

    return service.generateQuestion(token, technology, difficulty);
  }

  @Operation(summary = "Receber a resposta de uma questão", description = "Endpoint que vai ser chamado " +
          "quando um usuário responder uma questão",
    tags = {"Resposta de Atividades"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = String.class)
          )
        }
      ),
      @ApiResponse(description = "Questão não encontrada", responseCode = "404",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ExceptionResponse.class)
          )
        }
      ),
      @ApiResponse(description = "Questão já respondida por este usuário", responseCode = "409",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ExceptionResponse.class)
          )
        }
      ),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Forbiden", responseCode = "403", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
  @PostMapping("/responder")
  public ResponseEntity<String> answerQuestion(@RequestHeader("Authorization") String token,
                                          @RequestBody AnswerQuestionRequestDTO answerQuestionRequestDTO) {

    return service.answerQuestion(token, answerQuestionRequestDTO);
  }

}
