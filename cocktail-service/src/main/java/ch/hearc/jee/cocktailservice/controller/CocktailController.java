package ch.hearc.jee.cocktailservice.controller;

import ch.hearc.jee.cocktailservice.resource.Drink;
import ch.hearc.jee.cocktailservice.resource.JmsMessage;
import ch.hearc.jee.cocktailservice.service.CocktailService;
import ch.hearc.jee.cocktailservice.service.JmsSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cocktails")
public class CocktailController {

    private final CocktailService cocktailService;
    private final JmsSender jmsSender;
    private final String queueName;

    public CocktailController(CocktailService cocktailService, JmsSender jmsSender, @Value("${jms.feedback.queue}") String queueName) {
        this.cocktailService = cocktailService;
        this.jmsSender = jmsSender;
        this.queueName = queueName;
    }

    @GetMapping("/random")
    public ResponseEntity<Drink> getRandom() {
        Optional<Drink> drink = cocktailService.getRandom();
        if (drink.isPresent()) {
            return ResponseEntity.ok(drink.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Drink> search(@RequestParam String name) {
        Optional<Drink> drinks = cocktailService.search(name);
        if (drinks.isPresent()) {
            return ResponseEntity.ok(drinks.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/searchAll")
    public ResponseEntity<Drink[]> searchAll(@RequestParam String name) {
        Optional<Drink[]> drinks = cocktailService.searchAll(name);
        if (drinks.isPresent()) {
            return ResponseEntity.ok(drinks.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/feedback")
    public ResponseEntity<Void> feedback(@RequestBody JmsMessage feedback) throws JsonProcessingException {
        jmsSender.sendMessage(queueName, feedback);
        return ResponseEntity.noContent().build();
    }

}
