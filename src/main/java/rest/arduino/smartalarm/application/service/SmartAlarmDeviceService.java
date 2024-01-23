package rest.arduino.smartalarm.application.service;

import org.springframework.validation.BindingResult;
import rest.arduino.smartalarm.domain.dto.SmartDeviceDtoSaveRequest;

import java.security.Principal;

public interface SmartAlarmDeviceService {

    void addSmartAlarmDevice(SmartDeviceDtoSaveRequest smartDeviceDtoSaveRequest, Principal principal, BindingResult bindingResult);

    void deleteSmartAlarmDevice(Long deviceId);

}
