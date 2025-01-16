package devquest.application.controllers;

import devquest.application.model.services.impl.ExerciseServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/exercises")
public class ExerciseRestController {

  private ExerciseServiceImpl service;

  public ExerciseRestController(ExerciseServiceImpl exerciseService) {
    this.service = exerciseService;
  }

}
