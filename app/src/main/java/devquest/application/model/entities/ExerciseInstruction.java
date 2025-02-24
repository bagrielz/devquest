package devquest.application.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
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

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    ExerciseInstruction that = (ExerciseInstruction) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
