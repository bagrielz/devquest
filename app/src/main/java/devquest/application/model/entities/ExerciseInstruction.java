package devquest.application.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
  @Column
  private Long id;

  @Column(length = 1)
  private String indicator;

  @Column(columnDefinition = "TEXT")
  private String text;

  @ManyToOne
  private Exercise exercise;

}
