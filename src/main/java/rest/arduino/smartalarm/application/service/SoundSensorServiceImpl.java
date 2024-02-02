package rest.arduino.smartalarm.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rest.arduino.smartalarm.domain.dto.ScatterChartPoint;
import rest.arduino.smartalarm.domain.repository.SoundSensorRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SoundSensorServiceImpl implements SoundSensorService {

    private final SoundSensorRepository soundSensorRepository;

    @Override
    public List<ScatterChartPoint> getSoundSensorStatisticByMinute(String deviceMacId, Integer fromHour, Integer toHour, LocalDate localDate) {
        var averageValuesByMinute = soundSensorRepository.getAverageValuesByMinute(deviceMacId);

        List<ScatterChartPoint> soundSensorStatisticDtoList = new ArrayList<>();
        for (var map : averageValuesByMinute) {
            ScatterChartPoint photoSensorStatisticDto = new ScatterChartPoint();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.equals("minute")) {
                    photoSensorStatisticDto.setX((String) value);
                } else if (key.equals("averagevalue")) {
                    photoSensorStatisticDto.setY((Integer) value);
                }
            }
            soundSensorStatisticDtoList.add(photoSensorStatisticDto);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDate yesterday = localDate.minusDays(1);

        LocalDateTime fromDateTime;
        LocalDateTime toDateTime = LocalDateTime.parse(localDate + " " + String.format("%02d", toHour) + ":00", formatter);
        var soundDataStream = soundSensorStatisticDtoList.stream();

        if (fromHour < toHour) {
            fromDateTime = LocalDateTime.parse(localDate + " " + String.format("%02d", fromHour) + ":00", formatter);
        } else {
            fromDateTime = LocalDateTime.parse(yesterday + " " + String.format("%02d", fromHour) + ":00", formatter);
        }

        soundDataStream = soundDataStream.filter(soundData -> LocalDateTime.parse(soundData.getX(), formatter).isAfter(fromDateTime)
                && LocalDateTime.parse(soundData.getX(), formatter).isBefore(toDateTime));

        return soundDataStream.toList();
    }

}
