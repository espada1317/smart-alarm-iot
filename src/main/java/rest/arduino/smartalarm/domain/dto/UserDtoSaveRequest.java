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
public class UserDtoSaveRequest {

    @NotNull(message = "Nickname is mandatory")
    @Pattern(regexp = "^[A-Za-z0-9]{5,20}$",
            message = "Nickname must consist of 5 to 20 alphanumeric characters")
    private String nickname;

    @NotNull(message = "Email is mandatory")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",
            message = "Invalid email address")
    private String email;

    @NotNull(message = "Password is mandatory")
    @Pattern(regexp = "^.{6,20}$",
            message = "Password should be of 6 to 20 alphanumeric characters")
    private String password;

}
