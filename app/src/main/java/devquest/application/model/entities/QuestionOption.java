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

}
