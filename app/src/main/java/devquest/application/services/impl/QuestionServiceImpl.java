package devquest.application.services.impl;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.response.questions.QuestionResponseDTO;
import devquest.application.services.QuestionService;
import devquest.application.services.subservices.question.GenerateQuestionService;
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
