package devquest.application.controllers;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.exercises.ExerciseResponseDTO;
import devquest.application.model.services.impl.ExerciseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/exercicios")
public class ExerciseRestController {

  private ExerciseServiceImpl service;

  public ExerciseRestController(ExerciseServiceImpl exerciseService) {
    this.service = exerciseService;
  }

  @GetMapping("/gerar")
  public ResponseEntity<ExerciseResponseDTO> generateExercise(
          @RequestParam(name = "tecnologia") Technology technology,
          @RequestParam(name = "dificuldade") Difficulty difficulty
          ) {
    return service.generateExercise(technology, difficulty);
  }

}
