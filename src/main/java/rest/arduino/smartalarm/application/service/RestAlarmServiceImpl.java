package rest.arduino.smartalarm.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rest.arduino.smartalarm.domain.dto.CreationAlarmDto;
import rest.arduino.smartalarm.domain.dto.SensorValueDto;
import rest.arduino.smartalarm.domain.entity.Alarm;
import rest.arduino.smartalarm.domain.entity.PhotoSensor;
import rest.arduino.smartalarm.domain.entity.SmartAlarmDevice;
import rest.arduino.smartalarm.domain.entity.SoundSensor;
import rest.arduino.smartalarm.domain.entity.User;
import rest.arduino.smartalarm.domain.enums.AlarmStatus;
import rest.arduino.smartalarm.domain.repository.AlarmRepository;
import rest.arduino.smartalarm.domain.repository.PhotoSensorRepository;
import rest.arduino.smartalarm.domain.repository.SmartAlarmDeviceRepository;
import rest.arduino.smartalarm.domain.repository.SoundSensorRepository;
import rest.arduino.smartalarm.domain.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestAlarmServiceImpl implements RestAlarmService {

    private final AlarmRepository alarmRepository;

    private final UserRepository userRepository;

    private final SmartAlarmDeviceRepository smartAlarmDeviceRepository;

    private final PhotoSensorRepository photoSensorRepository;

    private final SoundSensorRepository soundSensorRepository;

    @Override
    public List<Alarm> getAllAlarms() {
        return alarmRepository.findAll();
    }

    @Override
    public Alarm getNextActiveAlarm(String deviceMacId) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        return getAllAlarms().stream()
                .filter(alarm -> alarm.getSmartAlarmDevice() != null)
                .filter(alarm -> alarm.getSmartAlarmDevice().getDeviceMacId().equals(deviceMacId))
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
    public void addAlarm(CreationAlarmDto creationAlarmDto, String username) {
        Alarm alarm = new Alarm();
        alarm.setCreationTimestamp(LocalDateTime.now());
        alarm.setLastModified(LocalDateTime.now());
        alarm.setStatus(AlarmStatus.ACTIVE);
        alarm.setAlarmDateTime(parseAlarmDateTime(creationAlarmDto.getHour(), creationAlarmDto.getMinute()));

        Optional<User> userOptional = userRepository.getUserByNickname(username);
        Optional<SmartAlarmDevice> smartAlarmDeviceOptional = smartAlarmDeviceRepository.findSmartAlarmDeviceByDeviceMacId(creationAlarmDto.getDeviceMacId());
        if (userOptional.isPresent() && smartAlarmDeviceOptional.isPresent()) {
            User user = userOptional.get();
            SmartAlarmDevice smartAlarmDevice = smartAlarmDeviceOptional.get();

            alarm.setUser(user);
            alarm.setSmartAlarmDevice(smartAlarmDevice);
        }

        alarmRepository.save(alarm);
    }

    @Override
    public void deleteAlarm(Long alarmId) {
        Optional<Alarm> alarmOptional = alarmRepository.findById(alarmId);
        if (alarmOptional.isPresent()) {
            Alarm alarm = alarmOptional.get();
            alarmRepository.delete(alarm);
        }
    }

    private LocalDateTime parseAlarmDateTime(Integer hour, Integer minute) {
        String dateValue = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        LocalTime parsedTime = LocalTime.parse(dateValue, DateTimeFormatter.ofPattern("HH:mm"));

        if (parsedTime.isAfter(LocalTime.now())) {
            return LocalDateTime.of(LocalDate.now(), parsedTime);
        } else {
            return LocalDateTime.of(LocalDate.now().plusDays(1), parsedTime);
        }
    }

    @Override
    public Alarm cancelCurrentAlarm(String deviceMacId) {
        Alarm currentAlarm = getNextActiveAlarm(deviceMacId);
        if (currentAlarm.getAlarmDateTime().toLocalDate().equals(LocalDate.now())
                && currentAlarm.getAlarmDateTime().toLocalTime().getHour() == LocalTime.now().getHour()
                && currentAlarm.getAlarmDateTime().toLocalTime().getMinute() == LocalTime.now().getMinute()) {
            currentAlarm.setStatus(AlarmStatus.DISABLED);
            alarmRepository.save(currentAlarm);
        }

        return currentAlarm;
    }

    @Override
    public void changeEnablementOfAlarm(String deviceMacId, Long id) {
        Optional<Alarm> alarmOptional = alarmRepository.findById(id);
        if (alarmOptional.isPresent()) {
            Alarm alarm = alarmOptional.get();
            if (alarm.getSmartAlarmDevice().getDeviceMacId().equals(deviceMacId)) {
                alarm.setStatus(alarm.getStatus().equals(AlarmStatus.ACTIVE) ? AlarmStatus.DISABLED : AlarmStatus.ACTIVE);
            }
            alarmRepository.save(alarm);
        }
    }

    @Override
    public PhotoSensor addPhotoSensorValue(String deviceMacId, SensorValueDto sensorValueDto) {
        PhotoSensor photoSensor = new PhotoSensor();
        photoSensor.setCreationTimestamp(LocalDateTime.now());
        photoSensor.setValue(Integer.parseInt(sensorValueDto.getSensorValue().trim()));

        Optional<SmartAlarmDevice> smartAlarmDeviceOptional = smartAlarmDeviceRepository.findSmartAlarmDeviceByDeviceMacId(deviceMacId);
        if (smartAlarmDeviceOptional.isPresent()) {
            SmartAlarmDevice smartAlarmDevice = smartAlarmDeviceOptional.get();
            photoSensor.setSmartAlarmDevice(smartAlarmDevice);
        }

        return photoSensorRepository.save(photoSensor);
    }

    @Override
    public SoundSensor addSoundSensorValue(String deviceMacId, SensorValueDto sensorValueDto) {
        SoundSensor soundSensor = new SoundSensor();
        soundSensor.setCreationTimestamp(LocalDateTime.now());
        soundSensor.setValue(Integer.parseInt(sensorValueDto.getSensorValue().trim()));

        Optional<SmartAlarmDevice> smartAlarmDeviceOptional = smartAlarmDeviceRepository.findSmartAlarmDeviceByDeviceMacId(deviceMacId);
        if (smartAlarmDeviceOptional.isPresent()) {
            SmartAlarmDevice smartAlarmDevice = smartAlarmDeviceOptional.get();
            soundSensor.setSmartAlarmDevice(smartAlarmDevice);
        }

        return soundSensorRepository.save(soundSensor);
    }

    @Override
    public Boolean verifyCurrentAlarm(String deviceMacId) {
        LocalTime currentTime = LocalDateTime.now().toLocalTime();
        if (getNextActiveAlarm(deviceMacId) == null) {
            return false;
        }
        LocalTime alarmTime = getNextActiveAlarm(deviceMacId).getAlarmDateTime().toLocalTime();

        int currentHour = currentTime.getHour();
        int currentMinute = currentTime.getMinute();

        int alarmHour = alarmTime.getHour();
        int alarmMinute = alarmTime.getMinute();

        return currentHour == alarmHour && currentMinute == alarmMinute;
    }

    @Override
    public List<Alarm> getAllAlarmsByUserAndDeviceMacId(String deviceMacId, String nickname) {
        Optional<User> userOptional = userRepository.getUserByNickname(nickname);
        Optional<SmartAlarmDevice> smartAlarmDeviceOptional = smartAlarmDeviceRepository.findSmartAlarmDeviceByDeviceMacId(deviceMacId);

        if (userOptional.isPresent() && smartAlarmDeviceOptional.isPresent()) {
            User user = userOptional.get();
            SmartAlarmDevice smartAlarmDevice = smartAlarmDeviceOptional.get();

            return alarmRepository.findAll().stream()
                    .filter(alarm -> alarm.getUser() != null)
                    .filter(alarm -> alarm.getUser().equals(user))
                    .filter(alarm -> alarm.getSmartAlarmDevice().equals(smartAlarmDevice))
                    .filter(alarm -> alarm.getAlarmDateTime().isAfter(LocalDateTime.now()))
                    .sorted(Comparator.comparing(Alarm::getAlarmDateTime))
                    .toList();
        }

        return List.of();
    }

}
