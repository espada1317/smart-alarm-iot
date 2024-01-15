package rest.arduino.smartalarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rest.arduino.smartalarm.domain.dto.CreationAlarmDto;
import rest.arduino.smartalarm.domain.entity.Alarm;
import rest.arduino.smartalarm.domain.enums.AlarmStatus;
import rest.arduino.smartalarm.domain.repository.AlarmRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestAlarmServiceImpl implements RestAlarmService {

    private final AlarmRepository alarmRepository;

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
        return getAllAlarms().stream()
                .filter(alarm -> alarm.getStatus().equals(AlarmStatus.ACTIVE))
                .filter(alarm -> alarm.getAlarmDateTime().isAfter(LocalDateTime.now()))
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

}
