package rest.arduino.smartalarm.application.service;

import rest.arduino.smartalarm.domain.dto.CreationAlarmDto;
import rest.arduino.smartalarm.domain.dto.SensorValueDto;
import rest.arduino.smartalarm.domain.entity.Alarm;
import rest.arduino.smartalarm.domain.entity.PhotoSensor;
import rest.arduino.smartalarm.domain.entity.SoundSensor;

import java.util.List;

public interface RestAlarmService {

    List<Alarm> getAllAlarms();

    Alarm getNextActiveAlarm(String deviceMacId);

    void addAlarm(CreationAlarmDto creationAlarmDto, String username);

    void deleteAlarm(Long alarmId);

    Alarm cancelCurrentAlarm(String deviceMacId);

    void changeEnablementOfAlarm(String deviceMacId, Long id);

    PhotoSensor addPhotoSensorValue(String deviceMacId, SensorValueDto sensorValueDto);

    SoundSensor addSoundSensorValue(String deviceMacId, SensorValueDto sensorValueDto);

    Boolean verifyCurrentAlarm(String deviceMacId);

    List<Alarm> getAllAlarmsByUserAndDeviceMacId(String deviceMacId, String nickname);

}
