package devquest.application.services.impl;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.dtos.request.AnswerQuestionRequestDTO;
import devquest.application.model.dtos.response.questions.QuestionResponseDTO;
import devquest.application.services.QuestionService;
import devquest.application.services.subservices.question.AnswerQuestionService;
import devquest.application.services.subservices.question.GenerateQuestionService;
import devquest.application.services.subservices.question.SearchUnansweredQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

  private GenerateQuestionService generateQuestionService;
  private SearchUnansweredQuestionService searchUnansweredQuestionService;
  private AnswerQuestionService answerQuestionService;

  public QuestionServiceImpl(GenerateQuestionService generateQuestionService,
                             SearchUnansweredQuestionService searchUnansweredQuestionService,
                             AnswerQuestionService answerQuestionService) {

    this.generateQuestionService = generateQuestionService;
    this.searchUnansweredQuestionService = searchUnansweredQuestionService;
    this.answerQuestionService = answerQuestionService;
  }

  @Override
  public ResponseEntity<QuestionResponseDTO> generateQuestion(String token,
                                                              Technology technology,
                                                              Difficulty difficulty) {

    QuestionResponseDTO unansweredQuestion = searchUnansweredQuestionService
            .getUnansweredQuestion(token, technology, difficulty);

    if (unansweredQuestion != null)
      return new ResponseEntity<>(unansweredQuestion, HttpStatus.OK);

    return generateQuestionService.generateQuestion(technology, difficulty);
  }

  @Override
  public ResponseEntity<String> answerQuestion(String token, AnswerQuestionRequestDTO answerQuestionRequestDTO) {
    return answerQuestionService.answerQuestion(token, answerQuestionRequestDTO);
  }

}
