package devquest.application.services.impl;

import devquest.application.repositories.UserProfileRepository;
import devquest.application.services.IUserProfileService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements IUserProfileService {

  private UserProfileRepository repository;

  public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
    this.repository = userProfileRepository;
  }

}
