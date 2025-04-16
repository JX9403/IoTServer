package com.system.iotserver.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.iotserver.dto.PagedResponse;
import com.system.iotserver.entity.Device;
import com.system.iotserver.entity.Sensor;
import com.system.iotserver.mqtt.MqttService;
import com.system.iotserver.service.SensorService;
import com.system.iotserver.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {
    private final SensorService sensorService;
    @Autowired
    private MqttService mqttService;
    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @GetMapping
    public ResponseEntity<PagedResponse<Sensor>> getSensors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String filterValue,
            @RequestParam(defaultValue = "all") String filterType,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        Pageable pageable = PageRequest.of(page, size, sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());

//        LocalDateTime filterDate = null;
//        if ("createdAt".equals(filterType) && filterValue != null && !filterValue.isEmpty()) {
//            try {
//                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
//                LocalDateTime.parse(filterValue, formatter);
//            } catch (DateTimeParseException e) {
//                return ResponseEntity.badRequest().body(new PagedResponse<>(List.of(), page, size, 0, 0));
//            }
//        }

        Page<Sensor> sensorPage = sensorService.getFilteredSensors(pageable, filterType, filterValue);

        PagedResponse<Sensor> response = new PagedResponse<>(
                sensorPage.getContent(),
                sensorPage.getNumber(),
                sensorPage.getSize(),
                sensorPage.getTotalElements(),
                sensorPage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Sensor> createSensor(@RequestBody Sensor sensor){
        sensor = sensorService.createSensor(sensor);
        return ResponseEntity.status(HttpStatus.CREATED).body(sensor);
    }

}