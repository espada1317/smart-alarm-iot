package rest.arduino.smartalarm.application.service;

import org.springframework.validation.BindingResult;
import rest.arduino.smartalarm.domain.dto.UserDtoSaveRequest;
import rest.arduino.smartalarm.domain.entity.User;

import java.util.Optional;

public interface UserService {

    void addUser(UserDtoSaveRequest userDtoSaveRequest, BindingResult bindingResult);

    Optional<User> getUserByNickname(String nickname);

}
