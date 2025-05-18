package ch.hearc.jee.feedbackservice;

import ch.hearc.jee.feedbackservice.Service.FeedbackService;
import ch.hearc.jee.feedbackservice.controller.FeedbackController;
import ch.hearc.jee.feedbackservice.model.Feedback;
import ch.hearc.jee.feedbackservice.model.JmsMessage;
import ch.hearc.jee.feedbackservice.Repository.FeedbackRepository;
import ch.hearc.jee.feedbackservice.Listener.FeedbackListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FeedbackServiceApplicationTests {

    private FeedbackRepository feedbackRepository;
    private FeedbackService feedbackService;
    private FeedbackController feedbackController;
    private FeedbackListener feedbackListener;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        feedbackRepository = mock(FeedbackRepository.class);
        feedbackService = new FeedbackService(feedbackRepository);
        feedbackController = new FeedbackController(feedbackService);
        objectMapper = new ObjectMapper();
        feedbackListener = new FeedbackListener(feedbackRepository, objectMapper);
    }

    @Test
    void testGetFeedbackByCocktailId_withNoFeedbacks() {
        when(feedbackRepository.findByCocktailId("123")).thenReturn(List.of());

        Map<String, Object> result = feedbackService.getFeedbackByCocktailId("123");

        assertTrue(((List<?>) result.get("feedbacks")).isEmpty());
        assertEquals(0.0, result.get("averageRating"));
    }

    @Test
    void testGetFeedbackByCocktailId_withSomeFeedbacks() {
        Feedback f1 = new Feedback();
        f1.setRating(4);
        Feedback f2 = new Feedback();
        f2.setRating(2);

        when(feedbackRepository.findByCocktailId("456")).thenReturn(List.of(f1, f2));

        Map<String, Object> result = feedbackService.getFeedbackByCocktailId("456");

        assertEquals(2, ((List<?>) result.get("feedbacks")).size());
        assertEquals(3.0, result.get("averageRating"));
    }

    @Test
    void testControllerReturnsExpectedResponse() {
        when(feedbackRepository.findByCocktailId("789")).thenReturn(List.of());

        ResponseEntity<Map<String, Object>> response = feedbackController.getFeedbackForCocktail("789");

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("feedbacks"));
    }

    @Test
    void testFeedbackListenerParsesAndSavesMessage() throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(new JmsMessage("999", "5", "Très bon"));

        feedbackListener.onFeedbackMessage(messageJson);

        ArgumentCaptor<Feedback> captor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository, times(1)).save(captor.capture());

        Feedback saved = captor.getValue();
        assertEquals("999", saved.getCocktailId());
        assertEquals("Très bon", saved.getComment());
        assertEquals(5, saved.getRating());
    }

    @Test
    void testFeedbackListenerIgnoresMessageWithBlankIdDrink() throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(new JmsMessage("", "3", "Très bon"));
        feedbackListener.onFeedbackMessage(messageJson);
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void testFeedbackListenerIgnoresMessageWithNonNumericIdDrink() throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(new JmsMessage("abc123", "3", "Très bon"));
        feedbackListener.onFeedbackMessage(messageJson);
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void testFeedbackListenerIgnoresMessageWithBlankMark() throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(new JmsMessage("123", "", "Très bon"));
        feedbackListener.onFeedbackMessage(messageJson);
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void testFeedbackListenerIgnoresMessageWithMarkOutOfRangeLow() throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(new JmsMessage("123", "0", "Très bon"));
        feedbackListener.onFeedbackMessage(messageJson);
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void testFeedbackListenerIgnoresMessageWithMarkOutOfRangeHigh() throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(new JmsMessage("123", "6", "Très bon"));
        feedbackListener.onFeedbackMessage(messageJson);
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void testFeedbackListenerIgnoresMessageWithNonNumericMark() throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(new JmsMessage("123", "abc", "Très bon"));
        feedbackListener.onFeedbackMessage(messageJson);
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void testFeedbackListenerIgnoresMessageWithBlankComment() throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(new JmsMessage("123", "3", ""));
        feedbackListener.onFeedbackMessage(messageJson);
        verify(feedbackRepository, never()).save(any());
    }
}