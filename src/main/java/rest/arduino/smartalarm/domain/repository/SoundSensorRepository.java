package rest.arduino.smartalarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.arduino.smartalarm.domain.entity.SoundSensor;

import java.util.List;
import java.util.Map;

@Repository
public interface SoundSensorRepository extends JpaRepository<SoundSensor, Long> {

    @Query(nativeQuery = true, value = """
            SELECT
                TO_CHAR(ss.creation_timestamp, 'YYYY-MM-DD HH24:MI') AS minute,
                CAST(ROUND(AVG(ss.value)) AS INTEGER) AS averageValue
            FROM
                sound_sensor ss
            JOIN
                smart_alarm_device sad ON sad.id = ss.smart_alarm_device_id
            WHERE
                sad.device_mac_id = :deviceMacId
            GROUP BY
                TO_CHAR(ss.creation_timestamp, 'YYYY-MM-DD HH24:MI')
            ORDER BY
                TO_CHAR(ss.creation_timestamp, 'YYYY-MM-DD HH24:MI');
            """)
    List<Map<String, Object>> getAverageValuesByMinute(String deviceMacId);

}
