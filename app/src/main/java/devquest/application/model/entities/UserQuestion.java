package devquest.application.model.entities;

import devquest.application.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_question")
public class UserQuestion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  @Column
  @Enumerated(EnumType.STRING)
  private Status status;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    UserQuestion that = (UserQuestion) o;
    return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(question, that.question) && status == that.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user, question, status);
  }
}
