package ch.hearc.jee.cocktailservice.resource;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class JmsMessage {
    @NotBlank(message = "Drink ID is required")
    @Pattern(regexp = "\\d+", message = "Drink ID must be a valid number")
    private String idDrink;

    @NotBlank(message = "Mark is required")
    @Pattern(regexp = "[1-5]", message = "Mark must be a number between 1 and 5")
    private String mark;

    @NotBlank(message = "Comment is required")
    private String comment;

    public JmsMessage() {}

    public JmsMessage(String idDrink, String mark, String comment) {
        this.idDrink = idDrink;
        this.mark = mark;
        this.comment = comment;
    }
}