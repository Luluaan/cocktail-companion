package ch.hearc.jee.cocktailservice.controller;

import ch.hearc.jee.cocktailservice.resource.Drink;
import ch.hearc.jee.cocktailservice.resource.JmsMessage;
import ch.hearc.jee.cocktailservice.service.CocktailFeedbackService;
import ch.hearc.jee.cocktailservice.service.CocktailService;
import ch.hearc.jee.cocktailservice.service.JmsSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cocktails")
public class CocktailController {

    private final CocktailService cocktailService;
    private final JmsSender jmsSender;
    private final CocktailFeedbackService feedbackService;
    private final String queueName;

    public CocktailController(CocktailService cocktailService,
                              JmsSender jmsSender,
                              @Value("${jms.feedback.queue}") String queueName,
                              CocktailFeedbackService feedbackService) {
        this.cocktailService = cocktailService;
        this.jmsSender = jmsSender;
        this.queueName = queueName;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/random")
    public ResponseEntity<Map<String, Object>> getRandom() {
        return cocktailService.getRandom()
                .map(drink -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("drink", drink);
                    result.put("feedback", feedbackService.getFeedbacks(drink.getId()));
                    return ResponseEntity.ok(result);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String name) {
        return cocktailService.search(name)
                .map(drink -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("drink", drink);
                    result.put("feedback", feedbackService.getFeedbacks(drink.getId()));
                    return ResponseEntity.ok(result);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/searchAll")
    public ResponseEntity<List<Map<String, Object>>> searchAll(@RequestParam String name) {
        return cocktailService.searchAll(name)
                .map(drinks -> {
                    List<Map<String, Object>> result = new ArrayList<>();
                    for (Drink drink : drinks) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("drink", drink);
                        item.put("feedback", feedbackService.getFeedbacks(drink.getId()));
                        result.add(item);
                    }
                    return ResponseEntity.ok(result);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/feedback")
    public ResponseEntity<?> feedback(@Valid @RequestBody JmsMessage feedback, BindingResult result) throws JsonProcessingException {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest().body(errorMessage);
        }

        jmsSender.sendMessage(queueName, feedback);
        return ResponseEntity.ok().build();
    }
}
