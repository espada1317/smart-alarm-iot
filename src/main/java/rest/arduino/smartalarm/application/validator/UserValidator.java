package rest.arduino.smartalarm.application.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import rest.arduino.smartalarm.domain.dto.UserDtoSaveRequest;
import rest.arduino.smartalarm.domain.entity.User;
import rest.arduino.smartalarm.domain.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validate(UserDtoSaveRequest userDto, Integer userId, Errors errors) {

        Optional<User> userByNickname = userRepository.getUserByNickname(userDto.getNickname());
        if (userByNickname.isPresent() && !Objects.equals(userByNickname.get().getId(), userId)) {
            errors.rejectValue("nickname", "", "Nickname must be unique");
        }

        Optional<User> userByEmail = userRepository.getUserByEmail(userDto.getEmail());
        if (userByEmail.isPresent() && !Objects.equals(userByEmail.get().getId(), userId)) {
            errors.rejectValue("email", "", "Email must be unique");
        }
    }

}