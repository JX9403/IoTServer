package com.system.iotserver.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    private final MqttGateway mqttGateway;

    @Autowired
    public MqttService(MqttGateway mqttGateway) {
        this.mqttGateway = mqttGateway;
    }

    // Phương thức gửi thông điệp MQTT
    public void publish(String topic, String payload) {
        mqttGateway.sendToMqtt(payload, topic);
        System.out.printf("📤 MQTT gửi: %s -> %s%n", topic, payload);
    }

    // Phương thức ánh xạ tên thiết bị sang topic
    public String mapDeviceToTopic(String deviceName) {
        return switch (deviceName) {
            case "fan" -> "devices/led1";
            case "light" -> "devices/led2";
            case "air-conditioner" -> "devices/led3";
            default -> null;
        };
    }
}
