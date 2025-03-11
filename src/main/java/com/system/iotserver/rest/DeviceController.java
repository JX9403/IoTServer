package com.system.iotserver.rest;

import com.system.iotserver.dto.PagedResponse;
import com.system.iotserver.entity.Device;
import com.system.iotserver.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<Device>> getDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Device> devicePage = deviceService.getAllDevices(pageable);

        PagedResponse<Device> response = new PagedResponse<>(
                devicePage.getContent(),
                devicePage.getNumber(),
                devicePage.getSize(),
                devicePage.getTotalElements(),
                devicePage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Device> getDeviceById(@PathVariable int id){
//        Device device = deviceService.getDeviceById(id);
//        if(device != null){
//            return ResponseEntity.ok(device);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device){
        device = deviceService.createDevice(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(device);
    }
}
