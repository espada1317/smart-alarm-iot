package rest.arduino.smartalarm.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rest.arduino.smartalarm.domain.deserializer.CustomDateTimeDeserializer;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreationAlarmDto {

    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private LocalDateTime alarmTime;

}
