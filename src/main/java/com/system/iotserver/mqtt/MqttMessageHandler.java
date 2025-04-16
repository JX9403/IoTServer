package com.system.iotserver.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.iotserver.entity.Sensor;
import com.system.iotserver.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class MqttMessageHandler {

    @Autowired
    private SensorService sensorService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handle(Message<?> message) {
        try {
            // Lấy payload từ message MQTT
            System.out.println("📥 Nhận tin nhắn từ MQTT: " + message.getPayload());
            String payload = (String) message.getPayload();

            Sensor sensor = objectMapper.readValue(payload, Sensor.class);

            // Lưu vào MySQL qua SensorService
            Sensor saved = sensorService.createSensor(sensor);
            System.out.println("✅ Saved to MySQL: " + saved);

        } catch (Exception e) {
            System.err.println("❌ Failed to handle MQTT message: " + e.getMessage());
        }
    }
}
