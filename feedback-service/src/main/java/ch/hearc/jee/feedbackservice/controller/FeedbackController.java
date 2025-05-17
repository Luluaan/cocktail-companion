package ch.hearc.jee.feedbackservice.controller;


import ch.hearc.jee.feedbackservice.Respository.LogRepository;
import ch.hearc.jee.feedbackservice.Service.FeedbackService;
import ch.hearc.jee.feedbackservice.model.Feedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {
    private final FeedbackService service;
    private final LogRepository logRepo;

    public FeedbackController(FeedbackService service, LogRepository logRepo) {
        this.service = service;
        this.logRepo = logRepo;
    }

    // Submit feedback (authenticated)
    @PostMapping("/{cocktailId}")
    public ResponseEntity<Void> postFeedback(
            @PathVariable String cocktailId,
            @RequestBody Feedback feedback
    ) {
        feedback.setCocktailId(cocktailId);
        if (feedback.getUserId() == null) {
            feedback.setUserId(UUID.randomUUID());
        }
        service.submitFeedback(feedback);
        return ResponseEntity.ok().build();
    }

    // Get feedbacks and view count
    @GetMapping("/{cocktailId}")
    public Map<String, Object> getFeedbacks(@PathVariable String cocktailId) {
        List<Feedback> list = service.getFeedbacks(cocktailId);
        long views = logRepo.countByCocktailId(cocktailId);
        Map<String, Object> response = new HashMap<>();
        response.put("feedbacks", list);
        response.put("viewCount", views);
        return response;
    }

}
