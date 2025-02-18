package devquest.application.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import devquest.application.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

}
