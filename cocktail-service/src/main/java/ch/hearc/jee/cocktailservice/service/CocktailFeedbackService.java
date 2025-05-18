package ch.hearc.jee.cocktailservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class CocktailFeedbackService implements CocktailFeedbackService_I {

    private final RestClient restClient;
    private final String feedbackServiceRoute;

    public CocktailFeedbackService(RestClient.Builder builder,
                                   @Value("${feedback.service.base.url}") String feedbackServiceBaseUrl,
                                   @Value("${feedback.service.route}") String feedbackServiceRoute){
        this.restClient = builder.baseUrl(feedbackServiceBaseUrl).build();
        this.feedbackServiceRoute = feedbackServiceRoute;
    }

    @Override
    public Map<String, Object> getFeedbacks(String cocktailId) {
        try {
            return restClient.get()
                    .uri(feedbackServiceRoute, cocktailId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (Exception e) {
            return Map.of();
        }
    }
}
