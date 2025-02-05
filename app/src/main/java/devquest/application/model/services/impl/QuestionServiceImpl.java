package devquest.application.model.services.impl;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.logics.AI.OpenaiCaller;
import devquest.application.logics.LogicGenerateQuestion;
import devquest.application.model.dtos.response.QuestionResponseDTO;
import devquest.application.model.entities.QuestionOption;
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
  private OpenaiCaller openaiCaller;

  public QuestionServiceImpl(QuestionRepository questionRepository,
                             LogicGenerateQuestion logicGenerateQuestion,
                             OpenaiCaller openaiCaller) {

    this.repository = questionRepository;
    this.logicGenerateQuestion = logicGenerateQuestion;
    this.openaiCaller = openaiCaller;
  }

  @Override
  public ResponseEntity<QuestionResponseDTO> generateQuestion(Technology technology, Difficulty difficulty) {
    String formattedPrompt = logicGenerateQuestion.cloneAndFormatPrompt(technology, difficulty);
    String questionString = openaiCaller.callOpenai(formattedPrompt);
    Question question = logicGenerateQuestion.createAndSaveQuestion(questionString, technology, difficulty);
    Set<QuestionOption> questionOptions = logicGenerateQuestion.saveOptionsInDatabase(questionString, question);
    question.setOptions(questionOptions);
    QuestionResponseDTO questionResponseDTO = logicGenerateQuestion.parseQuestionToQuestionResponseDTO(question);

    return new ResponseEntity<>(questionResponseDTO, HttpStatus.CREATED);
  }

}
