package devquest.application.model.services.impl;

import devquest.application.model.repositories.UserRepository;
import devquest.application.model.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository repository;

  public UserServiceImpl(UserRepository userRepository) {
    this.repository = userRepository;
  }

}
