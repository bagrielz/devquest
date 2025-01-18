package devquest.application.model.services.impl;

import devquest.application.config.PromptTemplate;
import devquest.application.model.repositories.QuestionRepository;
import devquest.application.model.services.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

  private QuestionRepository repository;
  private PromptTemplate prompt;

  public QuestionServiceImpl(QuestionRepository questionRepository, PromptTemplate promptTemplate) {
    this.repository = questionRepository;
    this.prompt = promptTemplate;
  }

}
