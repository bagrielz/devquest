package devquest.application.model.services.impl;

import devquest.application.model.repositories.QuestionRepository;
import devquest.application.model.services.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

  private QuestionRepository repository;

  public QuestionServiceImpl(QuestionRepository questionRepository) {
    this.repository = questionRepository;
  }

}
