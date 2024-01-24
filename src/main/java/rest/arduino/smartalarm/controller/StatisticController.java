package rest.arduino.smartalarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rest.arduino.smartalarm.application.service.PhotoSensorService;
import rest.arduino.smartalarm.application.service.SoundSensorService;
import rest.arduino.smartalarm.domain.dto.ScatterChartPoint;

import java.util.List;

@Controller
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final PhotoSensorService photoSensorService;

    private final SoundSensorService soundSensorService;

    @GetMapping()
    public String getStatisticPage(Model model) {
        List<ScatterChartPoint> photoSensorStatisticDtoList = photoSensorService.getPhotoSensorStatisticByMinute();
        List<ScatterChartPoint> soundSensorStatisticDtoList = soundSensorService.getSoundSensorStatisticByMinute();

        model.addAttribute("scatterPhotoData", photoSensorStatisticDtoList);
        model.addAttribute("scatterSoundData", soundSensorStatisticDtoList);

        return "statistic";
    }

}
