package ch.hearc.jee.feedbackservice.Listener;

import ch.hearc.jee.feedbackservice.Respository.FeedbackRepository;
import ch.hearc.jee.feedbackservice.model.Feedback;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class    FeedbackListener {
    private final FeedbackRepository repo;

    public FeedbackListener(FeedbackRepository repo) {
        this.repo = repo;
    }

    @JmsListener(destination = "feedback.requests")
    public void onFeedbackMessage(Feedback feedback) {
        feedback.setCreatedAt(Instant.now());
        repo.save(feedback);
    }
}
