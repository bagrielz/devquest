package devquest.application.services.impl;

import devquest.application.repositories.UserProfileRepository;
import devquest.application.services.UserProfileService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

  private UserProfileRepository repository;

  public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
    this.repository = userProfileRepository;
  }

}
