package rest.arduino.smartalarm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import rest.arduino.smartalarm.application.exception.ValidationCustomException;
import rest.arduino.smartalarm.application.service.UserService;
import rest.arduino.smartalarm.domain.dto.UserDtoSaveRequest;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping(value = "/")
    public String index() {
        return "redirect:/smartAlarm";
    }

    @GetMapping(value = "/signIn")
    public String signIn(Principal principal) {
        if (principal == null) {
            return "sign_in";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/signUp")
    public String signUpPage(Principal principal) {
        if (principal == null) {
            return "sign_up";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/signUp")
    public String addNewUser(@Valid @ModelAttribute("userInfo") UserDtoSaveRequest userInfo,
                             BindingResult bindingResult) {
        try {
            userService.addUser(userInfo, bindingResult);
        } catch (ValidationCustomException e) {
            return "redirect:/signUp?error";
        }
        return "redirect:/signUp?success";
    }

}
