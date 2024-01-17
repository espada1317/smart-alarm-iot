package rest.arduino.smartalarm.service;

import rest.arduino.smartalarm.domain.dto.CreationAlarmDto;
import rest.arduino.smartalarm.domain.dto.SensorValueDto;
import rest.arduino.smartalarm.domain.entity.Alarm;
import rest.arduino.smartalarm.domain.entity.PhotoSensor;

import java.util.List;

public interface RestAlarmService {

    List<Alarm> getAllAlarms();

    List<Alarm> getAllTodayAlarms();

    List<Alarm> getTodayActiveAlarms();

    Alarm getNextActiveAlarm();

    Alarm addAlarm(CreationAlarmDto creationAlarmDto);

    Alarm cancelCurrentAlarm();

    PhotoSensor addPhotoSensorValue(SensorValueDto sensorValueDto);

    Boolean verifyCurrentAlarm();

}
