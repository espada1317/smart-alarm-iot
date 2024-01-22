package rest.arduino.smartalarm.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import rest.arduino.smartalarm.application.exception.ValidationCustomException;
import rest.arduino.smartalarm.application.mapper.UserMapper;
import rest.arduino.smartalarm.application.validator.UserValidator;
import rest.arduino.smartalarm.domain.dto.UserDtoSaveRequest;
import rest.arduino.smartalarm.domain.entity.User;
import rest.arduino.smartalarm.domain.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserValidator userValidator;

    private final UserMapper userMapper;

    @Override
    public void addUser(UserDtoSaveRequest userDtoSaveRequest, BindingResult bindingResult) {
        userValidator.validate(userDtoSaveRequest, null, bindingResult);
        validateUserDtoRequest(bindingResult);

        User user = userMapper.userDtoRequestToUser(userDtoSaveRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("USER");

        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByNickname(String nickname) {
        return userRepository.getUserByNickname(nickname);
    }

    private void validateUserDtoRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationCustomException(bindingResult);
        }
    }

}
