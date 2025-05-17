package ch.hearc.jee.feedbackservice.Service;

import ch.hearc.jee.feedbackservice.Respository.FeedbackRepository;
import ch.hearc.jee.feedbackservice.model.Feedback;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Map<String, Object> getFeedbackByCocktailId(String cocktailId) {
        List<Feedback> feedbacks = feedbackRepository.findByCocktailId(cocktailId);

        double average = 0.0;
        if (!feedbacks.isEmpty()) {
            average = feedbacks.stream().mapToInt(Feedback::getRating).average().orElse(0.0);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("feedbacks", feedbacks);
        result.put("averageRating", average);

        return result;
    }
}
