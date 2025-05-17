package ch.hearc.jee.cocktailservice.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Drink {

    private String id;
    private String name;
    private String instructions;
    private String thumbnail;

    private List<String> ingredients;
    private List<String> measures;

    public Drink() {}

    public Drink(@JsonProperty("idDrink") String id,
                 @JsonProperty("strDrink") String name,
                 @JsonProperty("strInstructions") String instructions,
                 @JsonProperty("strDrinkThumb") String thumbnail,
                 @JsonProperty("strIngredient1") String strIngredient1,
                 @JsonProperty("strIngredient2") String strIngredient2,
                 @JsonProperty("strIngredient3") String strIngredient3,
                 @JsonProperty("strIngredient4") String strIngredient4,
                 @JsonProperty("strIngredient5") String strIngredient5,
                 @JsonProperty("strIngredient6") String strIngredient6,
                 @JsonProperty("strIngredient7") String strIngredient7,
                 @JsonProperty("strIngredient8") String strIngredient8,
                 @JsonProperty("strIngredient9") String strIngredient9,
                 @JsonProperty("strIngredient10") String strIngredient10,
                 @JsonProperty("strIngredient11") String strIngredient11,
                 @JsonProperty("strIngredient12") String strIngredient12,
                 @JsonProperty("strIngredient13") String strIngredient13,
                 @JsonProperty("strIngredient14") String strIngredient14,
                 @JsonProperty("strIngredient15") String strIngredient15,

                 @JsonProperty("strMeasure1") String strMeasure1,
                 @JsonProperty("strMeasure2") String strMeasure2,
                 @JsonProperty("strMeasure3") String strMeasure3,
                 @JsonProperty("strMeasure4") String strMeasure4,
                 @JsonProperty("strMeasure5") String strMeasure5,
                 @JsonProperty("strMeasure6") String strMeasure6,
                 @JsonProperty("strMeasure7") String strMeasure7,
                 @JsonProperty("strMeasure8") String strMeasure8,
                 @JsonProperty("strMeasure9") String strMeasure9,
                 @JsonProperty("strMeasure10") String strMeasure10,
                 @JsonProperty("strMeasure11") String strMeasure11,
                 @JsonProperty("strMeasure12") String strMeasure12,
                 @JsonProperty("strMeasure13") String strMeasure13,
                 @JsonProperty("strMeasure14") String strMeasure14,
                 @JsonProperty("strMeasure15") String strMeasure15) {

        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.thumbnail = thumbnail;

        this.ingredients = extractNonNull(
                strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
                strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
                strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15
        );

        this.measures = extractNonNull(
                strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
                strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
                strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15
        );
    }

    private List<String> extractNonNull(String... values) {
        List<String> result = new ArrayList<>();
        for (String val : values) {
            if (val != null && !val.trim().isEmpty()) {
                result.add(val.trim());
            }
        }
        return result;
    }
}