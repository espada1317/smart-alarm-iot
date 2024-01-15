package rest.arduino.smartalarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.arduino.smartalarm.domain.entity.RecurringAlarm;

import java.util.Optional;

@Repository
public interface RecurringAlarmRepository extends JpaRepository<RecurringAlarm, Long> {

    Optional<RecurringAlarm> findByIsMondayAndIsTuesdayAndIsWednesdayAndIsThursdayAndIsFridayAndIsSaturdayAndIsSunday(
            boolean isMonday, boolean isTuesday, boolean isWednesday, boolean isThursday,
            boolean isFriday, boolean isSaturday, boolean isSunday);

}
