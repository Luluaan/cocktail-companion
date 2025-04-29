package ch.hearc.jee.cocktailservice.controller;

import ch.hearc.jee.cocktailservice.service.CocktailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cocktails")
public class CocktailController {

    private final CocktailService cocktailService;

    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping("/random")
    public ResponseEntity<String> getRandom() {
        return ResponseEntity.ok(cocktailService.getRandom());
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String name) {
        return ResponseEntity.ok(cocktailService.search(name));
    }

}
