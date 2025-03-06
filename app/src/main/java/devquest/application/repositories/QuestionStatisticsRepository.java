package devquest.application.repositories;

import devquest.application.models.entities.QuestionsStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionStatisticsRepository extends JpaRepository<QuestionsStatistics, Long> {
}
