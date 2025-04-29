package ch.hearc.jee.cocktailservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
    public String getRandom() {
        return this.restClient.get()
                .uri(this.randomUrl)
                .retrieve()
                .body(String.class);
    }

    @Override
    public String search(String name) {
        return this.restClient.get()
                .uri(this.searchUrl, name)
                .retrieve()
                .body(String.class);
    }
}
