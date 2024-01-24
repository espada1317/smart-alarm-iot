package rest.arduino.smartalarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.arduino.smartalarm.application.service.RestAlarmService;
import rest.arduino.smartalarm.domain.dto.SensorValueDto;
import rest.arduino.smartalarm.domain.entity.Alarm;
import rest.arduino.smartalarm.domain.entity.PhotoSensor;
import rest.arduino.smartalarm.domain.entity.SoundSensor;

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

    @GetMapping("/alarms/{deviceMacId}/verify")
    public Boolean verifyCurrentAlarm(@PathVariable("deviceMacId") String deviceMacId) {
        return restAlarmService.verifyCurrentAlarm(deviceMacId);
    }

    @GetMapping("/alarms/{deviceMacId}/cancelCurrent")
    public ResponseEntity<Alarm> cancelAlarm(@PathVariable("deviceMacId") String deviceMacId) {
        return new ResponseEntity<>(restAlarmService.cancelCurrentAlarm(deviceMacId), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/sensors/photo/{deviceMacId}")
    public ResponseEntity<PhotoSensor> savePhotoSensorValue(@PathVariable("deviceMacId") String deviceMacId,
                                                            @RequestBody SensorValueDto sensorValueDto) {
        return new ResponseEntity<>(restAlarmService.addPhotoSensorValue(deviceMacId, sensorValueDto), HttpStatus.CREATED);
    }

    @PostMapping("/sensors/sound/{deviceMacId}")
    public ResponseEntity<SoundSensor> saveSoundSensorValue(@PathVariable("deviceMacId") String deviceMacId,
                                                            @RequestBody SensorValueDto sensorValueDto) {
        return new ResponseEntity<>(restAlarmService.addSoundSensorValue(deviceMacId, sensorValueDto), HttpStatus.CREATED);
    }

}
