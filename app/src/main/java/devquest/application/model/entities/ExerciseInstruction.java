package devquest.application.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

  @ManyToOne
  private Exercise exercise;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    ExerciseInstruction that = (ExerciseInstruction) o;
    return Objects.equals(id, that.id) && Objects.equals(indicator, that.indicator) && Objects.equals(text, that.text) && Objects.equals(exercise, that.exercise);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, indicator, text, exercise);
  }
}
