package devquest.application.controllers;

import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.QuestionResponseDTO;
import devquest.application.model.services.impl.QuestionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/questoes")
public class QuestionRestController {

  private QuestionServiceImpl service;

  public QuestionRestController(QuestionServiceImpl questionService) {
    this.service = questionService;
  }

  @GetMapping("/gerar")
  public ResponseEntity<QuestionResponseDTO> generateQuestion(
          @RequestParam(name = "tecnologia") Technology technology
          ) {
    return service.generateQuestion(technology);
  }

}
