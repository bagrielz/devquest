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
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "technology")
  private Technology technology;

  @Enumerated(EnumType.STRING)
  @Column(name = "difficulty")
  private Difficulty difficulty;

  @Column(name = "text", columnDefinition = "TEXT")
  private String text;

  @Column(name = "correct_answer", length = 1)
  private String correctAnswer;

  @Column(name = "justification", columnDefinition = "TEXT")
  private String justification;

  @Column(name = "created_at")
  private Date createdAt;

  @OneToMany(mappedBy = "question")
  private Set<QuestionOption> options;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private Set<UserQuestion> userQuestion;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Question question = (Question) o;
    return Objects.equals(id, question.id) && technology == question.technology && difficulty == question.difficulty && Objects.equals(text, question.text) && Objects.equals(correctAnswer, question.correctAnswer) && Objects.equals(justification, question.justification) && Objects.equals(createdAt, question.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, technology, difficulty, text, correctAnswer, justification, createdAt);
  }
}