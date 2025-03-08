package devquest.application.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
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

  @OneToOne(mappedBy = "questionsStatistics")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    QuestionsStatistics that = (QuestionsStatistics) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
