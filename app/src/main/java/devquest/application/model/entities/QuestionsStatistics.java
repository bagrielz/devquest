package devquest.application.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @OneToOne(mappedBy = "questionsStatistics")
  private User user;

}
