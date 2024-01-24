package rest.arduino.smartalarm.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rest.arduino.smartalarm.domain.dto.ScatterChartPoint;
import rest.arduino.smartalarm.domain.repository.SoundSensorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SoundSensorServiceImpl implements SoundSensorService {

    private final SoundSensorRepository soundSensorRepository;

    @Override
    public List<ScatterChartPoint> getSoundSensorStatisticByMinute() {
        var averageValuesByMinute = soundSensorRepository.getAverageValuesByMinute();

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

        return soundSensorStatisticDtoList;
    }

}
