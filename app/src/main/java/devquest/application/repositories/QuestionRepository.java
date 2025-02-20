package devquest.application.repositories;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.model.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  @Query("SELECT q FROM Question q WHERE q.technology = :technology AND q.difficulty = :difficulty")
  List<Question> findAllByTechnologyAndDifficulty(@Param("technology") Technology technology,
                                                  @Param("difficulty") Difficulty difficulty);

}
