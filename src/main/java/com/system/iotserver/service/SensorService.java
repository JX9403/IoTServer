package com.system.iotserver.service;


import com.system.iotserver.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface SensorService {

    public Page<Sensor> getFilteredSensors(Pageable pageable, String filterType, String filterValue);
    public Sensor createSensor(Sensor sensor);

}




