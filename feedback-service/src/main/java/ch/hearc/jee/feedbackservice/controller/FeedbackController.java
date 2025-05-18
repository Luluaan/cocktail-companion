package ch.hearc.jee.feedbackservice.controller;

import ch.hearc.jee.feedbackservice.Service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Operation(summary = "Get feedback for a cocktail", description = "Returns feedback for a specific cocktail by its ID")
    @GetMapping("/{cocktailId}")
    public ResponseEntity<Map<String, Object>> getFeedbackForCocktail(@PathVariable String cocktailId) {
        Map<String, Object> response = feedbackService.getFeedbackByCocktailId(cocktailId);
        return ResponseEntity.ok(response);
    }
}
