package devquest.application.repositories;

import devquest.application.models.entities.Question;
import devquest.application.models.entities.User;
import devquest.application.models.entities.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {

  @Query("SELECT uq FROM UserQuestion uq WHERE uq.user = :user AND uq.question = :question")
  Optional<UserQuestion> findByQuestionAndUserId(@Param("question") Question question,
                                                 @Param("user") User user);

}
