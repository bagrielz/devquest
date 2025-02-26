package devquest.application.models.entities;

import devquest.application.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user_profile")
public class UserProfile {

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

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  @OneToOne(mappedBy = "userProfile")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    UserProfile that = (UserProfile) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
