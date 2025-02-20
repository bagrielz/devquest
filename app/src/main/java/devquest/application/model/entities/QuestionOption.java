package devquest.application.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class QuestionOption {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "option_indicator", length = 2)
  private String optionIndicator;

  @Column(name = "option_text", columnDefinition = "TEXT")
  private String optionText;

  @ManyToOne
  private Question question;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    QuestionOption that = (QuestionOption) o;
    return Objects.equals(id, that.id) && Objects.equals(optionIndicator, that.optionIndicator) && Objects.equals(optionText, that.optionText) && Objects.equals(question, that.question);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, optionIndicator, optionText, question);
  }
}
