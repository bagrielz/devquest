package devquest.application.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class QuestionsStatistics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "correct_questions")
  private Integer correctQuestions;

  @Column(name = "exercises_completed")
  private Integer exercisesCompleted;

  @OneToOne
  @JoinColumn(name = "user_id", unique = true)
  private User user;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    QuestionsStatistics that = (QuestionsStatistics) o;
    return Objects.equals(id, that.id) && Objects.equals(correctQuestions, that.correctQuestions) && Objects.equals(exercisesCompleted, that.exercisesCompleted) && Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, correctQuestions, exercisesCompleted, user);
  }
}
