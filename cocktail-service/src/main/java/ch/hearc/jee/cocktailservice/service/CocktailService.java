package ch.hearc.jee.cocktailservice.service;

import ch.hearc.jee.cocktailservice.resource.Drink;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CocktailService implements CocktailService_I{

    private final RestClient restClient;
    private final String searchUrl;
    private final String randomUrl;

    public CocktailService(RestClient.Builder restClientBuilder,
                       @Value("${cocktaildb.api.base.url}") String baseUrl,
                       @Value("${cocktaildb.api.search.url}") String searchUrl,
                       @Value("${cocktaildb.api.random.url}") String randomUrl) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.searchUrl = searchUrl;
        this.randomUrl = randomUrl;
    }

    @Override
    public Optional<Drink> getRandom() {
        try {
            Map<String, Drink[]> map = this.restClient.get()
                    .uri(this.randomUrl)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            Drink[] drinksArray = map != null ? map.get("drinks") : null;

            if (drinksArray != null && drinksArray.length > 0) {
                return Optional.of(drinksArray[0]);
            } else {
                return Optional.empty();
            }
        } catch (HttpClientErrorException | ResourceAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Drink> search(String name) {
        try {
            Map<String, Object> map = this.restClient.get()
                    .uri(this.searchUrl, name)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            Object drinksObj = map != null ? map.get("drinks") : null;

            if (drinksObj instanceof List<?> drinksList && !drinksList.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                Drink[] drinks = mapper.convertValue(drinksList, Drink[].class);
                return Optional.of(drinks[0]);
            }
        } catch (HttpClientErrorException | ResourceAccessException ignored) {}

        return Optional.empty();
    }

    @Override
    public Optional<Drink[]> searchAll(String name) {
        try {
            Map<String, Object> map = this.restClient.get()
                    .uri(this.searchUrl, name)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            Object drinksObj = map != null ? map.get("drinks") : null;

            if (drinksObj instanceof List<?> drinksList) {
                ObjectMapper mapper = new ObjectMapper();
                Drink[] drinks = mapper.convertValue(drinksList, Drink[].class);
                return Optional.of(drinks);
            }
        } catch (HttpClientErrorException | ResourceAccessException ignored) {}

        return Optional.empty();
    }
}
