package rest.arduino.smartalarm.application.service;

import org.springframework.validation.BindingResult;
import rest.arduino.smartalarm.domain.dto.SmartDeviceDtoSaveRequest;
import rest.arduino.smartalarm.domain.entity.SmartAlarmDevice;

import java.security.Principal;
import java.util.Optional;

public interface SmartAlarmDeviceService {

    void addSmartAlarmDevice(SmartDeviceDtoSaveRequest smartDeviceDtoSaveRequest, Principal principal, BindingResult bindingResult);

    Optional<SmartAlarmDevice> getDeviceByMacId(String deviceMacId);

}
