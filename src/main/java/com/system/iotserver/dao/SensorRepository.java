package com.system.iotserver.dao;

import com.system.iotserver.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Page<Sensor> findByCreatedAtAfter(LocalDateTime createdAt, Pageable pageable);
    Page<Sensor> findByTemperature(double temperature, Pageable pageable);
    Page<Sensor> findByHumidity(double humidity, Pageable pageable);
    Page<Sensor> findByLight(double light, Pageable pageable);

    @Query("SELECT s FROM Sensor s WHERE " +
            "CAST(s.temperature AS string) LIKE %:value% OR " +
            "CAST(s.humidity AS string) LIKE %:value% OR " +
            "CAST(s.light AS string) LIKE %:value% OR " +
            "CAST(s.createdAt AS string) LIKE %:value%")
    Page<Sensor> findByAllFields(@Param("value") String value, Pageable pageable);
}

