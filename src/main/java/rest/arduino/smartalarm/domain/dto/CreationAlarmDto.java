package rest.arduino.smartalarm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreationAlarmDto {

    private String alarmTime;

    private boolean isMondayRecurring;

    private boolean isTuesdayRecurring;

    private boolean isWednesdayRecurring;

    private boolean isThursdayRecurring;

    private boolean isFridayRecurring;

    private boolean isSaturdayRecurring;

    private boolean isSundayRecurring;

}
