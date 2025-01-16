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

  @Column(name = "created_at")
  private Date createdAt;

  @ManyToMany(mappedBy = "exercises")
  private Set<AppUser> users;

}
