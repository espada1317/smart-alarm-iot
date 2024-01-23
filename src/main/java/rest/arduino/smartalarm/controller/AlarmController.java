package rest.arduino.smartalarm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rest.arduino.smartalarm.application.exception.ValidationCustomException;
import rest.arduino.smartalarm.application.service.RestAlarmService;
import rest.arduino.smartalarm.domain.dto.CreationAlarmDto;
import rest.arduino.smartalarm.domain.entity.Alarm;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final RestAlarmService restAlarmService;

    @GetMapping("/{deviceMacId}")
    public String getAllAlarmsForDeviceId(@PathVariable("deviceMacId") String deviceMacId,
                                          Principal principal,
                                          Model model) {
        List<Alarm> alarms = null;
        if (principal != null) {
            alarms = restAlarmService.getAllAlarmsByUserAndDeviceMacId(deviceMacId, principal.getName());
        }
        model.addAttribute("deviceMacId", deviceMacId);
        model.addAttribute("alarms", alarms);

        return "alarms";
    }

    @PostMapping(value = "/new")
    public String addNewAlarm(@ModelAttribute("alarmInfo") CreationAlarmDto creationAlarmDto,
                              Principal principal) {
        try {
            restAlarmService.addAlarm(creationAlarmDto, principal.getName());
        } catch (ValidationCustomException e) {
            log.error(e.getMessage());
        }
        return "redirect:/alarms/" + creationAlarmDto.getDeviceMacId();
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteAlarm(@PathVariable("id") Long id,
                              @ModelAttribute("alarmInfo") CreationAlarmDto creationAlarmDto) {
        restAlarmService.deleteAlarm(id);
        return "redirect:/alarms/" + creationAlarmDto.getDeviceMacId();
    }

}
