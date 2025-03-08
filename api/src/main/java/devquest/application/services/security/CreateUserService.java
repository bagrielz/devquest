package devquest.application.services.security;

import devquest.application.exceptions.RequiredObjectIsNullException;
import devquest.application.models.dtos.security.AccountCredentialsDTO;
import devquest.application.models.entities.Permission;
import devquest.application.models.entities.QuestionsStatistics;
import devquest.application.models.entities.User;
import devquest.application.models.entities.UserProfile;
import devquest.application.repositories.PermissionRepository;
import devquest.application.repositories.QuestionStatisticsRepository;
import devquest.application.repositories.UserProfileRepository;
import devquest.application.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreateUserService {

  private UserRepository repository;
  private PermissionRepository permissionRepository;
  private QuestionStatisticsRepository questionStatisticsRepository;
  private UserProfileRepository userProfileRepository;

  public CreateUserService(UserRepository repository,
                           PermissionRepository permissionRepository,
                           QuestionStatisticsRepository questionStatisticsRepository,
                           UserProfileRepository userProfileRepository) {

    this.repository = repository;
    this.permissionRepository = permissionRepository;
    this.questionStatisticsRepository = questionStatisticsRepository;
    this.userProfileRepository = userProfileRepository;
  }

  @Transactional
  public ResponseEntity<AccountCredentialsDTO> createUser(AccountCredentialsDTO userData) {
    if (invalidUserData(userData)) throw new RequiredObjectIsNullException("Invalid user data!");

    User user = createAndSaveUser(userData);
    relateQuestionsStatisticsWithUser(user);
    relateUserProfileWithUser(user);
    AccountCredentialsDTO accountCredentialsDTO = createAccountCredentialsDTO(user);
    return new ResponseEntity<>(accountCredentialsDTO, HttpStatus.CREATED);
  }

  private boolean invalidUserData(AccountCredentialsDTO userData) {
    return userData == null || userData.getUsername().isBlank()
            || userData.getFullname().isBlank() || userData.getPassword().isBlank();
  }

  private User createAndSaveUser(AccountCredentialsDTO userData) {
    User user = User.builder()
            .userName(userData.getUsername())
            .password(generateHashedPassword(userData.getPassword()))
            .fullName(userData.getFullname())
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .questionsStatistics(createQuestionStatistics())
            .userProfile(createUserProfile())
            .build();

    setUserPermissions(user);

    return repository.save(user);
  }

  private void setUserPermissions(User user) {
    Permission permission = permissionRepository.findById(3L).get();
    user.addPermission(permission);
  }

  private QuestionsStatistics createQuestionStatistics() {
    QuestionsStatistics questionsStatistics = QuestionsStatistics.builder()
            .correctQuestions(0)
            .exercisesCompleted(0)
            .build();

    return questionStatisticsRepository.save(questionsStatistics);
  }

  private UserProfile createUserProfile() {
    UserProfile userProfile = UserProfile.builder()
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();

    return userProfileRepository.save(userProfile);
  }

  private void relateQuestionsStatisticsWithUser(User user) {
    QuestionsStatistics questionsStatistics = user.getQuestionsStatistics();
    questionsStatistics.setUser(user);
    questionStatisticsRepository.save(questionsStatistics);
  }

  private void relateUserProfileWithUser(User user) {
    UserProfile userProfile = user.getUserProfile();
    userProfile.setUser(user);
    userProfileRepository.save(userProfile);
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
