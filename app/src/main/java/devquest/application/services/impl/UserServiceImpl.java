package devquest.application.services.impl;

import devquest.application.repositories.UserRepository;
import devquest.application.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  private UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
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

}
