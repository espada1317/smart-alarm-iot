package rest.arduino.smartalarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rest.arduino.smartalarm.domain.dto.CreationAlarmDto;
import rest.arduino.smartalarm.domain.entity.Alarm;
import rest.arduino.smartalarm.domain.entity.RecurringAlarm;
import rest.arduino.smartalarm.domain.enums.AlarmStatus;
import rest.arduino.smartalarm.domain.repository.AlarmRepository;
import rest.arduino.smartalarm.domain.repository.RecurringAlarmRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestAlarmServiceImpl implements RestAlarmService {

    private final AlarmRepository alarmRepository;

    private final RecurringAlarmRepository recurringAlarmRepository;

    @Override
    public List<Alarm> getAllAlarms() {
        return alarmRepository.findAll();
    }

    @Override
    public List<Alarm> getAllTodayAlarms() {
        return getAllAlarms().stream()
                .filter(alarm ->
                        (alarm.getIdRecurring() == null && alarm.getCreationTimestamp().toLocalDate().equals(LocalDate.now())
                                || alarm.getIdRecurring() != null && isRecurringAlarmForToday(alarm.getIdRecurring(), LocalDate.now().getDayOfWeek()))
                )
                .toList();
    }

    @Override
    public List<Alarm> getTodayActiveAlarms() {
        return getAllTodayAlarms().stream()
                .filter(alarm -> alarm.getStatus().equals(AlarmStatus.ACTIVE))
                .toList();
    }

    @Override
    public Alarm getNextActiveAlarm() {
        return getAllAlarms().stream()
                .filter(alarm -> alarm.getStatus().equals(AlarmStatus.ACTIVE))
                .filter(alarm -> getNextActiveDateTime(alarm) != null)
                .min(Comparator.comparing(this::getNextActiveDateTime,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);
    }

    @Override
    public Alarm addAlarm(CreationAlarmDto creationAlarmDto) {
        RecurringAlarm existingRecurringAlarm = findExistingRecurringAlarm(creationAlarmDto);

        if (existingRecurringAlarm == null) {
            RecurringAlarm newRecurringAlarm = RecurringAlarm.builder()
                    .isMonday(creationAlarmDto.isMondayRecurring())
                    .isTuesday(creationAlarmDto.isTuesdayRecurring())
                    .isWednesday(creationAlarmDto.isWednesdayRecurring())
                    .isThursday(creationAlarmDto.isThursdayRecurring())
                    .isFriday(creationAlarmDto.isFridayRecurring())
                    .isSaturday(creationAlarmDto.isSaturdayRecurring())
                    .isSunday(creationAlarmDto.isSundayRecurring())
                    .build();

            existingRecurringAlarm = recurringAlarmRepository.save(newRecurringAlarm);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Alarm alarm = new Alarm();
        alarm.setCreationTimestamp(LocalDateTime.now());
        alarm.setLastModified(LocalDateTime.now());
        alarm.setStatus(AlarmStatus.ACTIVE);
        alarm.setAlarmTime(LocalTime.parse(creationAlarmDto.getAlarmTime(), formatter));
        alarm.setIdRecurring(existingRecurringAlarm);

        return alarmRepository.save(alarm);
    }

    private RecurringAlarm findExistingRecurringAlarm(CreationAlarmDto creationAlarmDto) {
        return recurringAlarmRepository.findByIsMondayAndIsTuesdayAndIsWednesdayAndIsThursdayAndIsFridayAndIsSaturdayAndIsSunday(
                        creationAlarmDto.isMondayRecurring(), creationAlarmDto.isTuesdayRecurring(), creationAlarmDto.isWednesdayRecurring(),
                        creationAlarmDto.isThursdayRecurring(), creationAlarmDto.isFridayRecurring(), creationAlarmDto.isSaturdayRecurring(),
                        creationAlarmDto.isSundayRecurring())
                .orElse(null);
    }


    private boolean isRecurringAlarmForToday(RecurringAlarm recurring, DayOfWeek today) {
        return switch (today) {
            case MONDAY -> recurring.isMonday();
            case TUESDAY -> recurring.isTuesday();
            case WEDNESDAY -> recurring.isWednesday();
            case THURSDAY -> recurring.isThursday();
            case FRIDAY -> recurring.isFriday();
            case SATURDAY -> recurring.isSaturday();
            case SUNDAY -> recurring.isSunday();
        };
    }

    private LocalDateTime getNextActiveDateTime(Alarm alarm) {
        LocalTime alarmTime = alarm.getAlarmTime();
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();

        if (alarm.getIdRecurring() == null) {
            return (alarmTime.isAfter(now.toLocalTime())) ? LocalDateTime.of(today, alarmTime)
                    : LocalDateTime.of(today.plusDays(1), alarmTime);
        } else {
            LocalDate nextDate = today;
            boolean timeHasPassedToday = alarmTime.isBefore(now.toLocalTime());

            if (timeHasPassedToday || !isRecurringAlarmForToday(alarm.getIdRecurring(), today.getDayOfWeek())) {
                int daysChecked = 0;
                nextDate = nextDate.plusDays(1);
                while (!isRecurringAlarmForToday(alarm.getIdRecurring(), nextDate.getDayOfWeek())) {
                    nextDate = nextDate.plusDays(1);
                    daysChecked++;
                    if (daysChecked >= 7) {
                        return null;
                    }
                }
            }
            return LocalDateTime.of(nextDate, alarmTime);
        }
    }

}
