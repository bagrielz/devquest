package devquest.application.model.services;

import devquest.application.model.dtos.responses.QuestionResponseDTO;
import org.springframework.http.ResponseEntity;

public interface QuestionService {

  ResponseEntity<QuestionResponseDTO> generateQuestion(String technology);

}
