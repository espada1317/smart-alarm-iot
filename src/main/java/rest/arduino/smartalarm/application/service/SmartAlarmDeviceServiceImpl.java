package rest.arduino.smartalarm.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import rest.arduino.smartalarm.domain.dto.SmartDeviceDtoSaveRequest;
import rest.arduino.smartalarm.domain.entity.SmartAlarmDevice;
import rest.arduino.smartalarm.domain.entity.User;
import rest.arduino.smartalarm.domain.repository.SmartAlarmDeviceRepository;
import rest.arduino.smartalarm.domain.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SmartAlarmDeviceServiceImpl implements SmartAlarmDeviceService {

    private final SmartAlarmDeviceRepository smartAlarmDeviceRepository;

    private final UserRepository userRepository;

    @Override
    public void addSmartAlarmDevice(SmartDeviceDtoSaveRequest smartDeviceDtoSaveRequest,
                                    Principal principal,
                                    BindingResult bindingResult) {
        SmartAlarmDevice smartAlarmDevice = SmartAlarmDevice.builder()
                .deviceMacId(smartDeviceDtoSaveRequest.getDeviceMacId())
                .build();
        Optional<User> userOptional = userRepository.getUserByNickname(principal.getName());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            smartAlarmDevice.setUser(user);
            SmartAlarmDevice savedSmartAlarm = smartAlarmDeviceRepository.save(smartAlarmDevice);

            Set<SmartAlarmDevice> smartAlarmDevices = user.getSmartAlarms();
            smartAlarmDevices.add(savedSmartAlarm);
            user.setSmartAlarms(smartAlarmDevices);

            userRepository.save(user);
        }
    }

    @Override
    public Optional<SmartAlarmDevice> getDeviceByMacId(String deviceMacId) {
        return Optional.empty();
    }
}
