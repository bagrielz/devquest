package devquest.application.services.impl;

import devquest.application.repositories.UserProfileRepository;
import devquest.application.services.UserProfileService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileProfileServiceImpl implements UserProfileService {

  private UserProfileRepository repository;

  public UserProfileProfileServiceImpl(UserProfileRepository userProfileRepository) {
    this.repository = userProfileRepository;
  }

}
