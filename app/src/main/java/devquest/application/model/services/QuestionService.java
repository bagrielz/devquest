package devquest.application.model.services;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.questions.QuestionResponseDTO;
import org.springframework.http.ResponseEntity;

public interface QuestionService {

  ResponseEntity<QuestionResponseDTO> generateQuestion(Technology technology, Difficulty difficulty);

}
