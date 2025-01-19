package devquest.application.model.entities;

import devquest.application.enums.Technology;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
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

  @Column(name = "text", columnDefinition = "TEXT")
  private String text;

  @Column(name = "correct_answer", length = 1)
  private String correctAnswer;

  @Column(name = "justification", columnDefinition = "TEXT")
  private String justification;

  @Column(name = "created_at")
  private Date createdAt;

  @OneToMany(mappedBy = "question")
  private Set<Option> options;

  @ManyToMany(mappedBy = "questions")
  private Set<AppUser> users;

}
