package ch.hearc.jee.feedbackservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JmsMessage {
    private String idDrink;
    private String mark;
    private String comment;

    public JmsMessage() {}

    public JmsMessage(String idDrink, String mark, String comment) {
        this.idDrink = idDrink;
        this.mark = mark;
        this.comment = comment;
    }
}
