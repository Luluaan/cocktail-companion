package ch.hearc.jee.cocktailservice.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class CocktailFeedbackService implements CocktailFeedbackService_I {

    private final RestClient restClient;

    public CocktailFeedbackService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8080").build();
    }

    @Override
    public Map<String, Object> getFeedbacks(String cocktailId) {
        try {
            return restClient.get()
                    .uri("/feedback/{id}", cocktailId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (Exception e) {
            return Map.of();
        }
    }
}
