package com.system.iotserver.service;

import com.system.iotserver.dao.DeviceRepository;
import com.system.iotserver.dao.SensorRepository;
import com.system.iotserver.entity.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SensorServiceImpl implements SensorService{

    private SensorRepository sensorRepository;
    @Autowired
    public SensorServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Page<Sensor> getFilteredSensors(Pageable pageable, String filterType, String filterValue) {
        if (filterValue == null || filterValue.isEmpty()) {
            return sensorRepository.findAll(pageable);
        }

        try {
            switch (filterType.toLowerCase()) {
                case "temperature":
                    return sensorRepository.findByTemperature(Double.parseDouble(filterValue), pageable);
                case "humidity":
                    return sensorRepository.findByHumidity(Double.parseDouble(filterValue), pageable);
                case "light":
                    return sensorRepository.findByLight(Double.parseDouble(filterValue), pageable);
                case "createdat":
                    LocalDateTime filterDate = LocalDateTime.parse(filterValue, DateTimeFormatter.ISO_DATE_TIME);
                    return sensorRepository.findByCreatedAtAfter(filterDate, pageable);
                case "all":
                    return sensorRepository.findByAllFields(filterValue, pageable);
                default:
                    return sensorRepository.findAll(pageable);
            }
        } catch (Exception e) {
            return sensorRepository.findAll(pageable);
        }
    }

    @Override
    public Sensor createSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }
}
