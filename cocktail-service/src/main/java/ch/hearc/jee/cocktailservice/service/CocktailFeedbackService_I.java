package ch.hearc.jee.cocktailservice.service;

import java.util.Map;

public interface CocktailFeedbackService_I {
    Map<String, Object> getFeedbacks(String cocktailId);
}