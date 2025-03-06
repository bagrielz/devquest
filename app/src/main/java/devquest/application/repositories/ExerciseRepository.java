package devquest.application.repositories;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import devquest.application.models.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

  @Query("SELECT e FROM Exercise e WHERE e.technology = :technology AND e.difficulty = :difficulty")
  List<Exercise> findAllByTechnologyAndDifficulty(@PathVariable("technology") Technology technology,
                                                  @PathVariable("difficulty") Difficulty difficulty);


  @Query(value = "SELECT COUNT(*) = 0 FROM user_exercise WHERE user_id = :userId AND exercise_id = :exerciseId",
          nativeQuery = true)
  boolean userNotAnswerExercise(@PathVariable("exerciseId") Long exerciseId,
                                @PathVariable("userId") Long userId);

}
