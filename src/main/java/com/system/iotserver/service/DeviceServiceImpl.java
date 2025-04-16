package com.system.iotserver.service;

import com.system.iotserver.dao.DeviceRepository;
import com.system.iotserver.entity.Device;
import com.system.iotserver.mqtt.MqttService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final MqttService mqttService;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, MqttService mqttService) {
        this.deviceRepository = deviceRepository;
        this.mqttService = mqttService;
    }

    @Override
    public Page<Device> getAllDevices(Pageable pageable, String filterType, String filterValue) {
        if (filterValue == null || filterValue.isEmpty()) {
            return deviceRepository.findAll(pageable);
        }

        try {
            switch (filterType.toLowerCase()) {
                case "createdat":
                    LocalDateTime filterDate = LocalDateTime.parse(filterValue, DateTimeFormatter.ISO_DATE_TIME);
                    return deviceRepository.findByCreatedAt(filterDate, pageable);
                default:
                    return deviceRepository.findAll(pageable);
            }
        } catch (Exception e) {
            return deviceRepository.findAll(pageable);
        }
    }

    @Override
    @Transactional
    public Device createDevice(Device device) {
        // Lưu thiết bị vào DB
        device = deviceRepository.save(device);

        // Gửi thông điệp MQTT đến thiết bị
        sendMqttToDevice(device);

        return device;
    }

    // Phương thức gửi MQTT riêng
    public void sendMqttToDevice(Device device) {
        String topic = mqttService.mapDeviceToTopic(device.getName());
        if (topic != null && device.getAction() != null) {
            mqttService.publish(topic, device.getAction().toUpperCase());
        }
    }
}
