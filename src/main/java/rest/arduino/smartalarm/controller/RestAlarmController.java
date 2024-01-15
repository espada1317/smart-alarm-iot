package rest.arduino.smartalarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.arduino.smartalarm.domain.dto.CreationAlarmDto;
import rest.arduino.smartalarm.domain.entity.Alarm;
import rest.arduino.smartalarm.service.RestAlarmService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class RestAlarmController {

    private final RestAlarmService restAlarmService;

    @GetMapping("/currentTime")
    public String retrieveCurrentTime() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }

    @GetMapping("/alarms/today")
    public List<Alarm> retrieveListOfAllTodayAlarms() {
        return restAlarmService.getAllTodayAlarms();
    }

    @GetMapping("/alarms/today/active")
    public List<Alarm> retrieveListOfActiveTodayAlarms() {
        return restAlarmService.getTodayActiveAlarms();
    }

    @GetMapping("/alarms/next")
    public Alarm retrieveNextAlarm() {
        return restAlarmService.getNextActiveAlarm();
    }

    @PostMapping("/alarms")
    public ResponseEntity<Alarm> saveAlarm(@RequestBody CreationAlarmDto creationAlarmDto) {
        return new ResponseEntity<>(restAlarmService.addAlarm(creationAlarmDto), HttpStatus.CREATED);
    }

}
