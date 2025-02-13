package devquest.application.services.impl;

import devquest.application.repositories.UserProfileRepository;
import devquest.application.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserProfileRepository repository;

  public UserServiceImpl(UserProfileRepository userProfileRepository) {
    this.repository = userProfileRepository;
  }

}
