package rest.arduino.smartalarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.arduino.smartalarm.domain.entity.SmartAlarmDevice;

@Repository
public interface SmartAlarmDeviceRepository extends JpaRepository<SmartAlarmDevice, Long> {
}
