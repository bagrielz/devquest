package devquest.application.services.security;

import devquest.application.model.dtos.security.AccountCredentialsDTO;
import devquest.application.model.dtos.security.TokenDTO;
import devquest.application.model.entities.User;
import devquest.application.repositories.UserRepository;
import devquest.application.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

  public ResponseEntity<TokenDTO> signin(AccountCredentialsDTO data) {
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

  public ResponseEntity<TokenDTO> refreshToken(String username, String refreshToken) {
    var user = repository.findByUsername(username);
    if (user == null) throw new UsernameNotFoundException("Username" + username + " not found!");

    TokenDTO tokenDTO;
    tokenDTO = tokenProvider.refreshToken(refreshToken);
    return ResponseEntity.ok(tokenDTO);
  }



}
