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
public class ExerciseInstruction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "indicator", length = 1)
  private String indicator;

  @Column(name = "text", columnDefinition = "TEXT")
  private String text;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "exercise_id")
  private Exercise exercise;

}
