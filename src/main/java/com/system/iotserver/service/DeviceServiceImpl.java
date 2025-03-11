package com.system.iotserver.service;

import com.system.iotserver.dao.DeviceRepository;
import com.system.iotserver.entity.Device;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeviceServiceImpl implements DeviceService{
    private DeviceRepository deviceRepository;
    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    @Override
    public Page<Device> getAllDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable);
    }

//    @Override
//    public Device getDeviceById(int id) {
//        return deviceRepository.getById(id);
//    }
//
    @Override
    @Transactional
    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }
//
//    @Override
//    @Transactional
//    public void deleteDevice(int id) {
//        deviceRepository.deleteById(id);
//    }
//

}
