package ch.hearc.jee.feedbackservice.Listener;

import ch.hearc.jee.feedbackservice.Repository.FeedbackRepository;
import ch.hearc.jee.feedbackservice.model.Feedback;
import ch.hearc.jee.feedbackservice.model.JmsMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class FeedbackListener {

    private final ObjectMapper objectMapper;
    private final FeedbackRepository repo;

    public FeedbackListener(FeedbackRepository repo, ObjectMapper objectMapper) {
        this.repo = repo;
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${feedback.queue.name}")
    public void onFeedbackMessage(String message) throws JsonProcessingException {
        JmsMessage jmsMessage = objectMapper.readValue(message, JmsMessage.class);

        String idDrink = jmsMessage.getIdDrink();
        String markStr = jmsMessage.getMark();
        String comment = jmsMessage.getComment();

        if (idDrink == null || idDrink.isBlank() || !idDrink.matches("\\d+")) {
            return;
        }

        int mark = 0;
        try {
            mark = Integer.parseInt(markStr);
            if (mark < 1 || mark > 5) {
                return;
            }
        } catch (NumberFormatException e) {
            return;
        }

        if (comment == null || comment.isBlank()) {
            return;
        }

        Feedback feedback = new Feedback();
        feedback.setCocktailId(idDrink);
        feedback.setRating(mark);
        feedback.setComment(comment);

        repo.save(feedback);
    }
}