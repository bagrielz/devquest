package devquest.application.models.entities;

import devquest.application.enums.Difficulty;
import devquest.application.enums.Technology;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
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

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "exercise", cascade = CascadeType.ALL)
  private Set<ExerciseInstruction> instructions;

  @ManyToMany(mappedBy = "exercises")
  private Set<User> users;

  public void addInstruction(ExerciseInstruction exerciseInstruction) {
    if (instructions == null) instructions = new HashSet<>();
    instructions.add(exerciseInstruction);
  }

  public void addUser(User user) {
    if (users == null) users = new HashSet<>();
    users.add(user);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Exercise exercise = (Exercise) o;
    return Objects.equals(id, exercise.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
