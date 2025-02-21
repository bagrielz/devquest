package devquest.application.services.subservices.user;

import devquest.application.model.dtos.response.user.UserInfoDTO;
import devquest.application.model.entities.QuestionsStatistics;
import devquest.application.model.entities.User;
import devquest.application.repositories.UserRepository;
import devquest.application.utilities.TokenJwtDecoder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

  private UserRepository repository;
  private TokenJwtDecoder tokenJwtDecoder;

  public UserInfoService(UserRepository repository,
                         TokenJwtDecoder tokenJwtDecoder) {

    this.repository = repository;
    this.tokenJwtDecoder = tokenJwtDecoder;
  }

  public ResponseEntity<UserInfoDTO> getUserInfo(String token) {
    String username = tokenJwtDecoder.getTokenSubject(token);
    User user = repository.findByUsername(username);
    UserInfoDTO userInfoDTO = createUserInfoDTO(user.getId(), user.getFullName(), user.getQuestionsStatistics());

    return ResponseEntity.ok().body(userInfoDTO);
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
