package ch.hearc.jee.feedbackservice.Listener;

import ch.hearc.jee.feedbackservice.Respository.FeedbackRepository;
import ch.hearc.jee.feedbackservice.model.Feedback;
import ch.hearc.jee.feedbackservice.model.JmsMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class FeedbackListener {
    @Autowired
    private ObjectMapper objectMapper;

    private final FeedbackRepository repo;

    public FeedbackListener(FeedbackRepository repo) {
        this.repo = repo;
    }

    @JmsListener(destination = "feedback-queue")
    public void onFeedbackMessage(String message) throws JsonProcessingException {
        JmsMessage jmsMessage = objectMapper.readValue(message, JmsMessage.class);
        System.out.println("Message reçu : " + jmsMessage.getComment());
        Feedback feedback = new Feedback();
        feedback.setCocktailId(jmsMessage.getIdDrink());
        feedback.setComment(jmsMessage.getComment());

        try {
            feedback.setRating(Integer.parseInt(jmsMessage.getMark()));
        } catch (NumberFormatException e) {
            feedback.setRating(0);
        }

        repo.save(feedback);
    }
}