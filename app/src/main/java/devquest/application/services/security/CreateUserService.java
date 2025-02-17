package devquest.application.services.security;

import devquest.application.exceptions.RequiredObjectIsNullException;
import devquest.application.model.dtos.security.AccountCredentialsDTO;
import devquest.application.model.entities.Permission;
import devquest.application.model.entities.QuestionsStatistics;
import devquest.application.model.entities.User;
import devquest.application.repositories.PermissionRepository;
import devquest.application.repositories.QuestionStatisticsRepository;
import devquest.application.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CreateUserService {

  private UserRepository repository;
  private PermissionRepository permissionRepository;
  private QuestionStatisticsRepository questionStatisticsRepository;

  public CreateUserService(UserRepository repository,
                           PermissionRepository permissionRepository,
                           QuestionStatisticsRepository questionStatisticsRepository) {

    this.repository = repository;
    this.permissionRepository = permissionRepository;
    this.questionStatisticsRepository = questionStatisticsRepository;
  }

  public ResponseEntity<AccountCredentialsDTO> createUser(AccountCredentialsDTO userData) {
    if (invalidUserData(userData)) throw new RequiredObjectIsNullException("Invalid user data!");

    User user = createAndSetUserAttributes(userData);
    updateUserIdInQuestionStatistics(user);
    AccountCredentialsDTO accountCredentialsDTO = createAccountCredentialsDTO(user);
    return new ResponseEntity<>(accountCredentialsDTO, HttpStatus.CREATED);
  }

  private boolean invalidUserData(AccountCredentialsDTO userData) {
    return userData == null || userData.getUsername().isBlank()
            || userData.getFullname().isBlank() || userData.getPassword().isBlank();
  }

  private User createAndSetUserAttributes(AccountCredentialsDTO userData) {
    User user = new User();

    user.setUserName(userData.getUsername());
    user.setPassword(generateHashedPassword(userData.getPassword()));
    user.setFullName(userData.getFullname());
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setEnabled(true);
    user.setPermissions(setUserPermissions());
    user.setQuestionsStatistics(createAndSaveQuestionStatistics());

    return repository.save(user);
  }

  private List<Permission> setUserPermissions() {
    List<Permission> userPermission = new ArrayList<>();
    Permission permission = permissionRepository.findById(3L).get();
    userPermission.add(permission);

    return userPermission;
  }

  private QuestionsStatistics createAndSaveQuestionStatistics() {
    QuestionsStatistics questionsStatistics = QuestionsStatistics.builder()
            .correctQuestions(0)
            .exercisesCompleted(0)
            .build();

    return questionStatisticsRepository.save(questionsStatistics);
  }

  private void updateUserIdInQuestionStatistics(User user) {
    QuestionsStatistics questionsStatistics = user.getQuestionsStatistics();
    questionsStatistics.setUser(user);
    questionStatisticsRepository.save(questionsStatistics);
  }

  private AccountCredentialsDTO createAccountCredentialsDTO(User user) {
    return AccountCredentialsDTO.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .fullname(user.getFullName())
            .build();
  }

  private String generateHashedPassword(String password) {
    PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000,
            Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put("pbkdf2", pbkdf2Encoder);
    DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
    passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
    return passwordEncoder.encode(password);
  }

}
