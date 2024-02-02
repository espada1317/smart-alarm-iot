package rest.arduino.smartalarm.domain.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UserDtoUpdateRequest {

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",
            message = "Invalid email address")
    private String email;

    private Integer fromHour;

    private Integer toHour;

}