package rest.arduino.smartalarm.domain.dto;

import jakarta.validation.constraints.NotNull;
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
public class SmartDeviceDtoSaveRequest {

    @NotNull(message = "MAC ID is mandatory")
    @Pattern(regexp = "^[A-Za-z0-9]{15}$",
            message = "MAC ID must consist of 15 alphanumeric characters")
    private String deviceMacId;

}