package devquest.application.controllers;

import devquest.application.model.services.impl.QuestionServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/questions")
public class QuestionRestController {

  private QuestionServiceImpl service;

  public QuestionRestController(QuestionServiceImpl questionService) {
    this.service = questionService;
  }

}
