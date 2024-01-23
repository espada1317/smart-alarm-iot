package rest.arduino.smartalarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.arduino.smartalarm.application.service.RestAlarmService;
import rest.arduino.smartalarm.domain.dto.SensorValueDto;
import rest.arduino.smartalarm.domain.entity.Alarm;
import rest.arduino.smartalarm.domain.entity.PhotoSensor;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class RestAlarmController {

    private final RestAlarmService restAlarmService;

    @GetMapping("/currentTime")
    public String retrieveCurrentTime() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }

    @GetMapping("/alarms/next")
    public Alarm retrieveNextAlarm() {
        return restAlarmService.getNextActiveAlarm();
    }

    @GetMapping("/alarms/verify")
    public Boolean verifyCurrentAlarm() {
        return restAlarmService.verifyCurrentAlarm();
    }

    @GetMapping("/alarms/cancelCurrent")
    public ResponseEntity<Alarm> cancelAlarm() {
        return new ResponseEntity<>(restAlarmService.cancelCurrentAlarm(), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/sensors/photo")
    public ResponseEntity<PhotoSensor> saveAlarm(@RequestBody SensorValueDto sensorValueDto) {
        return new ResponseEntity<>(restAlarmService.addPhotoSensorValue(sensorValueDto), HttpStatus.CREATED);
    }

}
