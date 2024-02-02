package rest.arduino.smartalarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import rest.arduino.smartalarm.application.service.PhotoSensorService;
import rest.arduino.smartalarm.application.service.SoundSensorService;
import rest.arduino.smartalarm.application.service.UserService;
import rest.arduino.smartalarm.domain.dto.ScatterChartPoint;
import rest.arduino.smartalarm.domain.entity.User;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final PhotoSensorService photoSensorService;

    private final SoundSensorService soundSensorService;

    private final UserService userService;

    @GetMapping(value = "{deviceMacId}")
    public String getStatisticPage(@PathVariable("deviceMacId") String deviceMacId,
                                   Principal principal,
                                   Model model) {
        List<ScatterChartPoint> photoSensorStatisticDtoList = new ArrayList<>();
        List<ScatterChartPoint> soundSensorStatisticDtoList = new ArrayList<>();

        Optional<User> userOptional = userService.getUserByNickname(principal.getName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            photoSensorStatisticDtoList = photoSensorService.getPhotoSensorStatisticByMinute(deviceMacId, user.getFromHour(), user.getToHour(), LocalDate.now());
            soundSensorStatisticDtoList = soundSensorService.getSoundSensorStatisticByMinute(deviceMacId, user.getFromHour(), user.getToHour(), LocalDate.now());
        }

        model.addAttribute("scatterPhotoData", photoSensorStatisticDtoList);
        model.addAttribute("scatterSoundData", soundSensorStatisticDtoList);

        return "statistic";
    }

    @PostMapping(value = "{deviceMacId}")
    public String getStatisticPage(@PathVariable("deviceMacId") String deviceMacId,
                                   @RequestBody String dateObject,
                                   Principal principal,
                                   Model model) {
        List<ScatterChartPoint> photoSensorStatisticDtoList = new ArrayList<>();
        List<ScatterChartPoint> soundSensorStatisticDtoList = new ArrayList<>();

        String[] splittedString = dateObject.split("=");
        String selectedDate = splittedString.length == 2 ? splittedString[1] : "Today";
        DateTimeFormatter ymdformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate toDate = LocalDate.parse(selectedDate, ymdformatter);

        Optional<User> userOptional = userService.getUserByNickname(principal.getName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            photoSensorStatisticDtoList = photoSensorService.getPhotoSensorStatisticByMinute(deviceMacId, user.getFromHour(), user.getToHour(), toDate);
            soundSensorStatisticDtoList = soundSensorService.getSoundSensorStatisticByMinute(deviceMacId, user.getFromHour(), user.getToHour(), toDate);
        }

        model.addAttribute("scatterPhotoData", photoSensorStatisticDtoList);
        model.addAttribute("scatterSoundData", soundSensorStatisticDtoList);
        model.addAttribute("selectedDate", selectedDate);

        return "statistic";
    }

}
