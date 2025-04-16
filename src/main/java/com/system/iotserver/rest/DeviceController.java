package com.system.iotserver.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.iotserver.dto.PagedResponse;
import com.system.iotserver.entity.Device;
import com.system.iotserver.mqtt.MqttGateway;
import com.system.iotserver.mqtt.MqttService;
import com.system.iotserver.service.DeviceService;
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
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    private MqttService mqttService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<Device>> getDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String filterValue,
            @RequestParam(defaultValue = "createdAt") String filterType,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());


        Page<Device> devicePage = deviceService.getAllDevices(pageable,filterType,  filterValue);

        PagedResponse<Device> response = new PagedResponse<>(
                devicePage.getContent(),
                devicePage.getNumber(),
                devicePage.getSize(),
                devicePage.getTotalElements(),
                devicePage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        // 1. Lưu thiết bị vào DB


        // 2. Gửi MQTT nếu thiết bị hợp lệ
        String topic = mqttService.mapDeviceToTopic(device.getName());
        String action = device.getAction();

        if (topic != null && action != null) {
            mqttService.publish(topic, action.toUpperCase()); // ON / OFF
        } else {
            System.out.println("⚠️ Không thể gửi MQTT: thiếu topic hoặc action");
        }

        device = deviceService.createDevice(device);

        return ResponseEntity.status(HttpStatus.CREATED).body(device);
    }

}
