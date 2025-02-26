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

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.ALL)
  private Set<QuestionOption> options;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.ALL)
  private Set<UserQuestion> userQuestions;

  public void addOption(QuestionOption questionOption) {
    if (options == null) options = new HashSet<>();
    options.add(questionOption);
  }

  public void addUserQuestion(UserQuestion userQuestion) {
    if (userQuestions == null) userQuestions = new HashSet<>();
    userQuestions.add(userQuestion);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Question question = (Question) o;
    return Objects.equals(id, question.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}