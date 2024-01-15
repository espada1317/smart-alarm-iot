package rest.arduino.smartalarm.domain.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateValue = p.getValueAsString();

        try {
            return LocalDateTime.parse(dateValue, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e1) {
            try {
                LocalTime parsedTime = LocalTime.parse(dateValue, DateTimeFormatter.ofPattern("HH:mm"));

                if (parsedTime.isAfter(LocalTime.now())) {
                    return LocalDateTime.of(LocalDate.now(), parsedTime);
                } else {
                    return LocalDateTime.of(LocalDate.now().plusDays(1), parsedTime);
                }
            } catch (Exception e2) {
                throw new IOException("Failed to parse date: " + dateValue, e2);
            }
        }
    }

}
