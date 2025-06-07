package exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncorrectFormData extends RuntimeException {
    private String field;

    public IncorrectFormData(String message) {
        super(message);
    }
}
