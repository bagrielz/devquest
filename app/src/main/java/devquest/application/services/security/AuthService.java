package devquest.application.services.security;

import devquest.application.model.dtos.security.AccountCredentialsDTO;
import devquest.application.model.dtos.security.TokenDTO;
import devquest.application.repositories.UserRepository;
import devquest.application.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private JwtTokenProvider tokenProvider;
  private AuthenticationManager authenticationManager;
  private UserRepository repository;

  public AuthService(JwtTokenProvider jwtTokenProvider,
                     AuthenticationManager authenticationManager,
                     UserRepository repository) {

    this.tokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
    this.repository = repository;
  }

  public ResponseEntity signin(AccountCredentialsDTO data) {
    try {
      var username = data.getUsername();
      var password = data.getPassword();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      var user = repository.findByUsername(username);
      var tokenResponse = new TokenDTO();
      if (user != null) {
        tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
      } else {
        throw new UsernameNotFoundException("Username" + username + " not found!");
      }

      return ResponseEntity.ok(tokenResponse);
    } catch (Exception e) {
      throw new BadCredentialsException("Invalid username/password supplied!");
    }
  }

}
