package devquest.application.services.subservices.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import devquest.application.model.dtos.response.user.UserInfoDTO;
import devquest.application.model.entities.QuestionsStatistics;
import devquest.application.model.entities.User;
import devquest.application.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserInfoService {

  @Value("${security.jwt.token.secret-key}")
  private String secretKey;

  Algorithm alg = null;

  private UserRepository repository;

  public UserInfoService(UserRepository repository) {
    this.repository = repository;
  }

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    alg = Algorithm.HMAC256(secretKey.getBytes());
  }

  public ResponseEntity<UserInfoDTO> getUserInfo(String token) {
    String username = decodeToken(token).getSubject();
    User user = repository.findByUsername(username);
    UserInfoDTO userInfoDTO = createUserInfoDTO(user.getId(), user.getFullName(), user.getQuestionsStatistics());

    return ResponseEntity.ok().body(userInfoDTO);
  }

  private DecodedJWT decodeToken(String token) {
    token = token.substring("Bearer ".length());
    JWTVerifier verifier = JWT.require(alg).build();
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT;
  }

  private UserInfoDTO createUserInfoDTO(Long userId, String userFullName, QuestionsStatistics questionsStatistics) {
    var userCorrectQuestions = questionsStatistics.getCorrectQuestions();
    var userExercisesCompleted = questionsStatistics.getExercisesCompleted();

    return UserInfoDTO.builder()
            .userId(userId)
            .fullName(userFullName)
            .correctQuestions(userCorrectQuestions)
            .exercisesCompleted(userExercisesCompleted)
            .build();
  }

}
