package com.ninja.rmm.controller;


import com.ninja.rmm.exception.CustomerNotFoundException;
import com.ninja.rmm.exception.DeviceNotFoundException;
import com.ninja.rmm.model.Device;
import com.ninja.rmm.repository.CustomerRepository;
import com.ninja.rmm.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DeviceController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * lis all devices assigned to a customer via GET http://localhost:8080/customers/{customerId}/devices
     *
     * @param customerId represents the id of the customer
     * @return Lis of Device objects
     */
    @GetMapping("/customers/{customerId}/devices")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Device> getAllDevicesByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        return deviceRepository.findByCustomerId(customerId);
    }

    /**
     * Create a device and assign to a customer via POST http://localhost:8080/customers/{customerId}/devices
     *
     * @param customerId represents the id of the customer
     * @param device     Device object been passed via request body
     * @return Device object
     */
    @PostMapping("/customers/{customerId}/devices")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Device createDevice(@PathVariable(value = "customerId") Long customerId,
                               @Valid @RequestBody Device device) {
        return customerRepository.findById(customerId).map(customer -> {
            device.setCustomer(customer);
            return deviceRepository.save(device);
        }).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    /**
     * Update an existing device via PUT http://localhost:8080/customers/{customerId}/devices/{deviceId}
     *
     * @param customerId    represents the id of the customer
     * @param deviceId      represents the id of the device
     * @param deviceRequest represents the request object been passed
     * @return Device object
     */
    @PutMapping("/customers/{customerId}/devices/{deviceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Device updateDevice(@PathVariable(value = "customerId") Long customerId,
                               @PathVariable(value = "deviceId") Long deviceId,
                               @Valid @RequestBody Device deviceRequest) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        return deviceRepository.findById(deviceId).map(device -> {
            device.setSystemName(deviceRequest.getSystemName());
            device.setDeviceType(deviceRequest.getDeviceType());
            device.setDeviceCost(deviceRequest.getDeviceCost());
            return deviceRepository.save(device);
        }).orElseThrow(() -> new DeviceNotFoundException(deviceId));
    }

    /**
     * Delete a device via DELETE http://localhost:8080/customers/{customerId}/devices/{deviceId}
     *
     * @param customerId represents the id of the customer
     * @param deviceId   represents the id of the device
     * @return ResponseEntity
     */
    @DeleteMapping("/customers/{customerId}/devices/{deviceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> deleteDevice(@PathVariable(value = "customerId") Long customerId,
                                          @PathVariable(value = "deviceId") Long deviceId) {
        return deviceRepository.findByIdAndCustomerId(deviceId, customerId).map(device -> {
            deviceRepository.delete(device);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new DeviceNotFoundException(deviceId));
    }
}
