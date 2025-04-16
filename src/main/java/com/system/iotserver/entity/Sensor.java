package com.system.iotserver.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column ( name = "temperature")
    private double temperature;
    @Column ( name = "humidity")
    private double  humidity;
    @Column ( name = "light")
    private double light ;
//new
//    @Column ( name = "cloud")
//    private double cloud ;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME(0)")
    private LocalDateTime createdAt;

    public Sensor() {
    }
    public Sensor( double temperature, double humidity, double light) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
    }
//new
//    public Sensor(double temperature, double humidity, double light, double cloud) {
//        this.temperature = temperature;
//        this.humidity = humidity;
//        this.light = light;
//        this.cloud = cloud;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }

//    new
//    public double getCloud() {
//        return cloud;
//    }
//
//    public void setCloud(double cloud) {
//        this.cloud = cloud;
//    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
