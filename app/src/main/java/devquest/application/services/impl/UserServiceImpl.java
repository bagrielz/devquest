package devquest.application.services.impl;

import devquest.application.model.dtos.response.user.UserInfoDTO;
import devquest.application.repositories.UserRepository;
import devquest.application.services.UserService;
import devquest.application.services.subservices.user.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  private UserRepository repository;
  private UserInfoService userInfoService;

  public UserServiceImpl(UserRepository repository,
                         UserInfoService userInfoService) {

    this.repository = repository;
    this.userInfoService = userInfoService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = repository.findByUsername(username);
    if (user != null) {
      return user;
    } else {
      throw new UsernameNotFoundException("Username " + username + " not found!");
    }
  }

  @Override
  public ResponseEntity<UserInfoDTO> getUserInfo(String token) {
    return userInfoService.getUserInfo(token);
  }

}
