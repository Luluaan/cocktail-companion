package ch.hearc.jee.cocktailservice;

import ch.hearc.jee.cocktailservice.controller.CocktailController;
import ch.hearc.jee.cocktailservice.resource.JmsMessage;
import ch.hearc.jee.cocktailservice.service.CocktailFeedbackService;
import ch.hearc.jee.cocktailservice.service.CocktailService;
import ch.hearc.jee.cocktailservice.service.JmsSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CocktailServiceApplicationTests {

    @Mock
    private CocktailService cocktailService;

    @Mock
    private JmsSender jmsSender;

    @Mock
    private CocktailFeedbackService feedbackService;

    @InjectMocks
    private CocktailController cocktailController;

    private final String queueName = "testQueue";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cocktailController = new CocktailController(cocktailService, jmsSender, queueName, feedbackService);
    }

    @Test
    void testFeedback_validMessage_sendsAndReturnsOk() throws JsonProcessingException {
        // Arrange
        JmsMessage validMessage = new JmsMessage("123", "5", "Très bon");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        ResponseEntity<?> response = cocktailController.feedback(validMessage, bindingResult);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(jmsSender, times(1)).sendMessage(queueName, validMessage);
    }

    @Test
    void testFeedback_invalidMessage_returnsBadRequestWithErrors() throws JsonProcessingException {
        JmsMessage invalidMessage = new JmsMessage("", "0", "");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        FieldError error1 = new FieldError("jmsMessage", "idDrink", "Drink ID is required");
        FieldError error2 = new FieldError("jmsMessage", "mark", "Mark must be a number between 1 and 5");
        FieldError error3 = new FieldError("jmsMessage", "comment", "Comment is required");

        when(bindingResult.getAllErrors()).thenReturn(List.of(error1, error2, error3));

        ResponseEntity<?> response = cocktailController.feedback(invalidMessage, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        String body = (String) response.getBody();
        assertTrue(body.contains("Drink ID is required"));
        assertTrue(body.contains("Mark must be a number between 1 and 5"));
        assertTrue(body.contains("Comment is required"));

        verify(jmsSender, never()).sendMessage(anyString(), any());
    }

}
