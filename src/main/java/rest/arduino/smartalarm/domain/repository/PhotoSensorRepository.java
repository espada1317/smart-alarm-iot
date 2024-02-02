package rest.arduino.smartalarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.arduino.smartalarm.domain.entity.PhotoSensor;

import java.util.List;
import java.util.Map;

public interface PhotoSensorRepository extends JpaRepository<PhotoSensor, Long> {

    @Query(nativeQuery = true, value = """
            SELECT
                TO_CHAR(ps.creation_timestamp, 'YYYY-MM-DD HH24:MI') AS minute,
                CAST(ROUND(AVG(ps.value)) AS INTEGER) AS averageValue
            FROM
                photo_sensor ps
            JOIN
                smart_alarm_device sad ON sad.id = ps.smart_alarm_device_id
            WHERE
                sad.device_mac_id = :deviceMacId
            GROUP BY
                TO_CHAR(ps.creation_timestamp, 'YYYY-MM-DD HH24:MI')
            ORDER BY
                TO_CHAR(ps.creation_timestamp, 'YYYY-MM-DD HH24:MI');
            """)
    List<Map<String, Object>> getAverageValuesByMinute(String deviceMacId);

}
