package com.system.iotserver.service;

import com.system.iotserver.entity.Device;
import com.system.iotserver.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DeviceService {
    public Page<Device> getAllDevices(Pageable pageable, String filterType, String filterValue);
    public Device createDevice(Device device);

}
