package com.system.iotserver.service;

import com.system.iotserver.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DeviceService {
    public Page<Device> getAllDevices(Pageable pageable);
//    public Device getDeviceById(int id);
    public Device createDevice(Device device);
//    public void deleteDevice (int id);
}
