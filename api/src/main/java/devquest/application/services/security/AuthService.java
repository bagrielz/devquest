package devquest.application.services.security;

import devquest.application.models.dtos.security.AccountCredentialsDTO;
import devquest.application.models.dtos.security.TokenDTO;
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
        throw new UsernameNotFoundException("Usuário " + username + " não encontrado");
      }

      return ResponseEntity.ok(tokenResponse);
    } catch (Exception e) {
      throw new BadCredentialsException("Usuário ou senha incorretos");
    }
  }

  public ResponseEntity<TokenDTO> refreshToken(String username, String refreshToken) {
    var user = repository.findByUsername(username);
    if (user == null) throw new UsernameNotFoundException("Usuário " + username + " não encontrado");

    TokenDTO tokenDTO;
    tokenDTO = tokenProvider.refreshToken(refreshToken);
    return ResponseEntity.ok(tokenDTO);
  }



}
