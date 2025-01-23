package devquest.application.model.services.impl;

import devquest.application.enums.Technology;
import devquest.application.logics.LogicGenerateQuestion;
import devquest.application.model.dtos.response.QuestionResponseDTO;
import devquest.application.model.entities.Option;
import devquest.application.model.entities.Question;
import devquest.application.model.repositories.QuestionRepository;
import devquest.application.model.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {

  private QuestionRepository repository;
  private LogicGenerateQuestion logicGenerateQuestion;

  public QuestionServiceImpl(QuestionRepository questionRepository,
                             LogicGenerateQuestion logicGenerateQuestion) {
    this.repository = questionRepository;
    this.logicGenerateQuestion = logicGenerateQuestion;
  }

  @Override
  public ResponseEntity<QuestionResponseDTO> generateQuestion(Technology technology) {
    String formattedPrompt = logicGenerateQuestion.formatprompt(technology);
    String questionString = logicGenerateQuestion.callOpenaiAndReturnResponse(formattedPrompt);
    Question question = logicGenerateQuestion.createQuestionObjectAndSaveInDatabase(questionString, technology);
    Set<Option> options = logicGenerateQuestion.saveOptionsInDatabaseAndReturnAnOptionSet(questionString, question);
    question.setOptions(options);
    QuestionResponseDTO questionResponseDTO = logicGenerateQuestion.parseQuestionToQuestionResponseDTO(question);

    return new ResponseEntity<>(questionResponseDTO, HttpStatus.CREATED);
  }

}
