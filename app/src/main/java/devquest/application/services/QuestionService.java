package devquest.application.services;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.request.AnswerQuestionRequestDTO;
import devquest.application.model.dtos.response.questions.QuestionResponseDTO;
import org.springframework.http.ResponseEntity;

public interface QuestionService {

  ResponseEntity<QuestionResponseDTO> generateQuestion(String token, Technology technology, Difficulty difficulty);
  ResponseEntity<String> answerQuestion(String token, AnswerQuestionRequestDTO answerQuestionRequestDTO);

}
