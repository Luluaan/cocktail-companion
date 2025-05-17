package ch.hearc.jee.cocktailservice.service;

import ch.hearc.jee.cocktailservice.resource.Drink;

import java.util.Optional;

public interface CocktailService_I {

    Optional<Drink> getRandom();
    Optional<Drink> search(String name);
    Optional<Drink[]> searchAll(String name);
}
