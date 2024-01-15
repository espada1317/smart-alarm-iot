package rest.arduino.smartalarm.service;

import rest.arduino.smartalarm.domain.dto.CreationAlarmDto;
import rest.arduino.smartalarm.domain.entity.Alarm;

import java.util.List;

public interface RestAlarmService {

    List<Alarm> getAllAlarms();

    List<Alarm> getAllTodayAlarms();

    List<Alarm> getTodayActiveAlarms();

    Alarm getNextActiveAlarm();

    Alarm addAlarm(CreationAlarmDto creationAlarmDto);

}
