package devquest.application.controllers;

import devquest.application.model.dtos.responses.QuestionResponseDTO;
import devquest.application.model.services.impl.QuestionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/questions")
public class QuestionRestController {

  private QuestionServiceImpl service;

  public QuestionRestController(QuestionServiceImpl questionService) {
    this.service = questionService;
  }

  @GetMapping("/gerar")
  public ResponseEntity<QuestionResponseDTO> generateQuestion(
          @RequestParam(name = "tecnologia") String technology
  ) {
    return service.generateQuestion(technology);
  }

}
