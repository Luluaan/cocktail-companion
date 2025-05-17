package ch.hearc.jee.cocktailservice.controller;

import ch.hearc.jee.cocktailservice.resource.Drink;
import ch.hearc.jee.cocktailservice.service.CocktailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cocktails")
public class CocktailController {

    private final CocktailService cocktailService;

    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
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
    public ResponseEntity<Drink[]> search(@RequestParam String name) {
        Optional<Drink[]> drinks = cocktailService.search(name);
        if (drinks.isPresent()) {
            return ResponseEntity.ok(drinks.get());
        }
        return ResponseEntity.notFound().build();
    }

}
