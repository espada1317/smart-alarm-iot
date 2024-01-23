package rest.arduino.smartalarm.application.service;

import rest.arduino.smartalarm.domain.dto.CreationAlarmDto;
import rest.arduino.smartalarm.domain.dto.SensorValueDto;
import rest.arduino.smartalarm.domain.entity.Alarm;
import rest.arduino.smartalarm.domain.entity.PhotoSensor;

import java.util.List;

public interface RestAlarmService {

    List<Alarm> getAllAlarms();

    List<Alarm> getAllTodayAlarms();

    Alarm getNextActiveAlarm();

    void addAlarm(CreationAlarmDto creationAlarmDto, String username);

    void deleteAlarm(Long alarmId);

    Alarm cancelCurrentAlarm();

    PhotoSensor addPhotoSensorValue(SensorValueDto sensorValueDto);

    Boolean verifyCurrentAlarm();

    List<Alarm> getAllAlarmsByUserAndDeviceMacId(String deviceMacId, String nickname);

}
