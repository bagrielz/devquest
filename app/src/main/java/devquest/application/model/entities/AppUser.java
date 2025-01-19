package devquest.application.model.entities;

import devquest.application.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "cpf", length = 11, unique = true)
  private String cpf;

  @Column(name = "first_name", length = 50)
  private String firstName;

  @Column(name = "last_name", length = 100)
  private String lastName;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "email", length = 100, unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private QuestionsStatistics questionsStatistics;

  @ManyToMany
  @JoinTable(name = "user_exercise",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "exercise_id")
  )
  private Set<Exercise> exercises;

  @ManyToMany
  @JoinTable(name = "user_question",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "question_id")
  )
  private Set<Question> questions;

}
