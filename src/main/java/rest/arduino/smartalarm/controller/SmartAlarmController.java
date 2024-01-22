package rest.arduino.smartalarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rest.arduino.smartalarm.application.exception.ValidationCustomException;
import rest.arduino.smartalarm.application.service.SmartAlarmDeviceService;
import rest.arduino.smartalarm.application.service.UserService;
import rest.arduino.smartalarm.domain.dto.SmartDeviceDtoSaveRequest;
import rest.arduino.smartalarm.domain.entity.SmartAlarmDevice;
import rest.arduino.smartalarm.domain.entity.User;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/smartAlarm")
@RequiredArgsConstructor
public class SmartAlarmController {

    private final UserService userService;

    private final SmartAlarmDeviceService smartAlarmDeviceService;

    @GetMapping
    public String getAllSmartAlarmDevices(Principal principal,
                                          Model model) {
        Set<SmartAlarmDevice> smartAlarmDevices = null;

        if (principal != null) {
            Optional<User> response = userService.getUserByNickname(principal.getName());

            if (response.isPresent()) {
                smartAlarmDevices = response.get().getSmartAlarms();
            }
        }
        model.addAttribute("smartAlarmList", smartAlarmDevices);

        return "smart_alarms";
    }

    @PostMapping(value = "/new")
    public String addNewSmartAlarmDevice(@ModelAttribute("smartAlarmInfo") SmartDeviceDtoSaveRequest smartAlarmInfo,
                                         Principal principal,
                                         BindingResult bindingResult) {
        try {
            smartAlarmDeviceService.addSmartAlarmDevice(smartAlarmInfo, principal, bindingResult);
        } catch (ValidationCustomException e) {
            return "redirect:/smartAlarm?error";
        }
        return "redirect:/smartAlarm?success";
    }

}
