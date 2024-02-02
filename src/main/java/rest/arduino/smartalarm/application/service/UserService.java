package rest.arduino.smartalarm.application.service;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import rest.arduino.smartalarm.domain.dto.UserDtoSaveRequest;
import rest.arduino.smartalarm.domain.dto.UserDtoUpdateRequest;
import rest.arduino.smartalarm.domain.entity.User;

import java.security.Principal;
import java.util.Optional;

public interface UserService {

    void addUser(UserDtoSaveRequest userDtoSaveRequest, BindingResult bindingResult);

    Optional<User> getUserByNickname(String nickname);

    void updateUser(@Valid UserDtoUpdateRequest dtoUpdateRequest, Principal principal, BindingResult bindingResult);

}
