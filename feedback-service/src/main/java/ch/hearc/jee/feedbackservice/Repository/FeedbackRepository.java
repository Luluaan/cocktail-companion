package ch.hearc.jee.feedbackservice.Repository;

import ch.hearc.jee.feedbackservice.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCocktailId(String cocktailId);
}
