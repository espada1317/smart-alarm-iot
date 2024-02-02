package rest.arduino.smartalarm.application.service;

import rest.arduino.smartalarm.domain.dto.ScatterChartPoint;

import java.time.LocalDate;
import java.util.List;

public interface SoundSensorService {

    List<ScatterChartPoint> getSoundSensorStatisticByMinute(String deviceMacId, Integer fromHour, Integer toHour, LocalDate localDate);

}
