package devquest.application.model.entities;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Exercise {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "technology")
  private Technology technology;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(name = "difficulty")
  private Difficulty difficulty;

  @Column(name = "created_at")
  private Date createdAt;

  @OneToMany(mappedBy = "exercise")
  private Set<ExerciseInstruction> instructions;

  @ManyToMany(mappedBy = "exercises")
  private Set<User> users;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Exercise exercise = (Exercise) o;
    return Objects.equals(id, exercise.id) && technology == exercise.technology && Objects.equals(content, exercise.content) && difficulty == exercise.difficulty && Objects.equals(createdAt, exercise.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, technology, content, difficulty, createdAt);
  }
}
