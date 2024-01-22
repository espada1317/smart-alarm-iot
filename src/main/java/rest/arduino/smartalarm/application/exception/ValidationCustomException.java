package rest.arduino.smartalarm.application.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.TreeMap;

@Getter
public class ValidationCustomException extends RuntimeException {

    private final Map<String, String> errors = new TreeMap<>();

    public ValidationCustomException(Errors errors) {
        this.errors.putAll(errorsToMap(errors));
    }

    private Map<String, String> errorsToMap(Errors errors) {
        if (errors.hasErrors()) {
            Map<String, String> errorsMap = new TreeMap<>();
            errors.getFieldErrors().forEach(error -> {
                if (!errorsMap.containsKey(error.getField())) {
                    errorsMap.put(error.getField(), error.getDefaultMessage());
                }
            });
            return errorsMap;
        }

        return new TreeMap<>();
    }

}
