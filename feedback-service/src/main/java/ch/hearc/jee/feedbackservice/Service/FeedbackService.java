package ch.hearc.jee.feedbackservice.Service;


import ch.hearc.jee.feedbackservice.Respository.FeedbackRepository;
import ch.hearc.jee.feedbackservice.model.Feedback;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeedbackService {
    private final JmsTemplate jms;
    private final FeedbackRepository repo;

    public FeedbackService(JmsTemplate jms, FeedbackRepository repo) {
        this.jms = jms;
        this.repo = repo;
    }

    public void submitFeedback(Feedback feedback) {
        jms.convertAndSend("feedback.requests", feedback);
    }

    public List<Feedback> getFeedbacks(String cocktailId) {
        return repo.findByCocktailId(cocktailId);
    }
}
