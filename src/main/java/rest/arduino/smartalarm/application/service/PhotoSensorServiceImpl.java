package rest.arduino.smartalarm.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rest.arduino.smartalarm.domain.dto.ScatterChartPoint;
import rest.arduino.smartalarm.domain.repository.PhotoSensorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PhotoSensorServiceImpl implements PhotoSensorService {

    private final PhotoSensorRepository photoSensorRepository;

    @Override
    public List<ScatterChartPoint> getPhotoSensorStatisticByMinute() {
        var averageValuesByMinute = photoSensorRepository.getAverageValuesByMinute();

        List<ScatterChartPoint> photoSensorStatisticDtoList = new ArrayList<>();
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
            photoSensorStatisticDtoList.add(photoSensorStatisticDto);
        }

        return photoSensorStatisticDtoList;
    }
}
