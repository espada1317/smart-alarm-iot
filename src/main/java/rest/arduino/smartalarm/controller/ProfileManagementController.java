package rest.arduino.smartalarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import rest.arduino.smartalarm.application.service.UserService;
import rest.arduino.smartalarm.domain.dto.UserDtoUpdateRequest;
import rest.arduino.smartalarm.domain.entity.User;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProfileManagementController {

    private final UserService userService;

    @GetMapping(value = "/updateProfile")
    public String updateProfilePage(Principal principal,
                                    Model model) {
        if (principal != null) {
            Optional<User> user = userService.getUserByNickname(principal.getName());

            if (user.isPresent()) {
                model.addAttribute("principalName", principal.getName());
                model.addAttribute("userDetails", user.get());
            }
        }
        return "update_profile";
    }

    @PostMapping(value = "/updateProfileText", params = "action=update")
    public String modifyUserSettings(@ModelAttribute("userSettingsDto") UserDtoUpdateRequest userSettingsDto,
                                     Principal principal,
                                     BindingResult bindingResult) {
        userService.updateUser(userSettingsDto, principal, bindingResult);
        return "redirect:/updateProfile?success";
    }

    @PostMapping(value = "/updateProfileText", params = "action=cancel")
    public String cancelModifying() {
        return "redirect:/";
    }

}