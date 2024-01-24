package rest.arduino.smartalarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.arduino.smartalarm.domain.entity.SoundSensor;

@Repository
public interface SoundSensorRepository extends JpaRepository<SoundSensor, Long> {
}
