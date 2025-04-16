package com.system.iotserver.dao;

import com.system.iotserver.entity.Device;
import com.system.iotserver.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Page<Device> findByCreatedAt(LocalDateTime createdAt, Pageable pageable);
}
