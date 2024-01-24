package rest.arduino.smartalarm.application.service;

import rest.arduino.smartalarm.domain.dto.ScatterChartPoint;

import java.util.List;

public interface PhotoSensorService {

    List<ScatterChartPoint> getPhotoSensorStatisticByMinute();

}
