package devquest.application.utilities;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class TokenJwtDecoder {

  @Value("${security.jwt.token.secret-key}")
  private String secretKey;

  private Algorithm algorithm = null;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    algorithm = Algorithm.HMAC256(secretKey.getBytes());
  }

  public String getTokenSubject(String token) {
    DecodedJWT decodedToken = decodeToken(token);
    return decodedToken.getSubject();
  }

  private DecodedJWT decodeToken(String token) {
    token = token.substring("Bearer ".length());
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT;
  }

}
