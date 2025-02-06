package devquest.application.model.services.impl;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.QuestionResponseDTO;
import devquest.application.model.services.QuestionService;
import devquest.application.model.services.subservices.question.GenerateQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

  private GenerateQuestionService generateQuestionService;

  public QuestionServiceImpl(GenerateQuestionService generateQuestionService) {
    this.generateQuestionService = generateQuestionService;
  }

  @Override
  public ResponseEntity<QuestionResponseDTO> generateQuestion(Technology technology, Difficulty difficulty) {
    return generateQuestionService.generateQuestion(technology, difficulty);
  }

}
