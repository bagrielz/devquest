package devquest.application.repositories;

import devquest.application.model.entities.ExerciseInstruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseInstructionRepository extends JpaRepository<ExerciseInstruction, Long> {
}
