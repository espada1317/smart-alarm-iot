package rest.arduino.smartalarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.arduino.smartalarm.domain.entity.PhotoSensor;

public interface PhotoSensorRepository extends JpaRepository<PhotoSensor, Long> {
}
