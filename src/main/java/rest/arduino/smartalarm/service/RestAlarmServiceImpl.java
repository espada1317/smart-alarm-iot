package rest.arduino.smartalarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rest.arduino.smartalarm.domain.dto.CreationAlarmDto;
import rest.arduino.smartalarm.domain.dto.SensorValueDto;
import rest.arduino.smartalarm.domain.entity.Alarm;
import rest.arduino.smartalarm.domain.entity.PhotoSensor;
import rest.arduino.smartalarm.domain.enums.AlarmStatus;
import rest.arduino.smartalarm.domain.repository.AlarmRepository;
import rest.arduino.smartalarm.domain.repository.PhotoSensorRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestAlarmServiceImpl implements RestAlarmService {

    private final AlarmRepository alarmRepository;

    private final PhotoSensorRepository photoSensorRepository;

    @Override
    public List<Alarm> getAllAlarms() {
        return alarmRepository.findAll();
    }

    @Override
    public List<Alarm> getAllTodayAlarms() {
        return getAllAlarms().stream()
                .filter(alarm -> alarm.getAlarmDateTime().toLocalDate().equals(LocalDate.now()))
                .sorted(Comparator.comparing(Alarm::getAlarmDateTime,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    @Override
    public List<Alarm> getTodayActiveAlarms() {
        return getAllTodayAlarms().stream()
                .filter(alarm -> alarm.getStatus().equals(AlarmStatus.ACTIVE))
                .sorted(Comparator.comparing(Alarm::getAlarmDateTime,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    @Override
    public Alarm getNextActiveAlarm() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        return getAllAlarms().stream()
                .filter(alarm -> alarm.getStatus().equals(AlarmStatus.ACTIVE))
                .filter(alarm ->
                        (alarm.getAlarmDateTime().toLocalDate().equals(LocalDate.now()) &&
                                alarm.getAlarmDateTime().getHour() == currentDateTime.getHour() &&
                                alarm.getAlarmDateTime().getMinute() == currentDateTime.getMinute()) || alarm.getAlarmDateTime().isAfter(currentDateTime))
                .min(Comparator.comparing(Alarm::getAlarmDateTime,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);
    }

    @Override
    public Alarm addAlarm(CreationAlarmDto creationAlarmDto) {
        Alarm alarm = new Alarm();
        alarm.setCreationTimestamp(LocalDateTime.now());
        alarm.setLastModified(LocalDateTime.now());
        alarm.setStatus(AlarmStatus.ACTIVE);
        alarm.setAlarmDateTime(creationAlarmDto.getAlarmTime());

        return alarmRepository.save(alarm);
    }

    @Override
    public PhotoSensor addPhotoSensorValue(SensorValueDto sensorValueDto) {
        PhotoSensor photoSensor = new PhotoSensor();
        photoSensor.setCreationTimestamp(LocalDateTime.now());
        photoSensor.setValue(Integer.parseInt(sensorValueDto.getSensorValue().trim()));

        return photoSensorRepository.save(photoSensor);
    }

    @Override
    public Boolean verifyCurrentAlarm() {
        LocalTime currentTime = LocalDateTime.now().toLocalTime();
        LocalTime alarmTime = getNextActiveAlarm().getAlarmDateTime().toLocalTime();

        int currentHour = currentTime.getHour();
        int currentMinute = currentTime.getMinute();

        int alarmHour = alarmTime.getHour();
        int alarmMinute = alarmTime.getMinute();

        return currentHour == alarmHour && currentMinute == alarmMinute;
    }

}
