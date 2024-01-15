package rest.arduino.smartalarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.arduino.smartalarm.domain.entity.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
