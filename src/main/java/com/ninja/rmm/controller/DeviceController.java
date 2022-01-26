package com.ninja.rmm.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ninja.rmm.exception.CustomerNotFoundException;
import com.ninja.rmm.exception.DeviceNotFoundException;
import com.ninja.rmm.model.Device;
import com.ninja.rmm.model.DeviceModelAssembler;
import com.ninja.rmm.repository.CustomerRepository;
import com.ninja.rmm.repository.DeviceRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController {

    private final DeviceModelAssembler deviceModelAssembler;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    public DeviceController(DeviceModelAssembler deviceModelAssembler) {
        this.deviceModelAssembler = deviceModelAssembler;
    }

    /**
     * lis all devices assigned to a customer via GET http://localhost:8080/customers/{customerId}/devices
     *
     * @param customerId represents the id of the customer
     * @return Lis of Device objects
     */
    @GetMapping("/customers/{customerId}/devices")
    @ResponseStatus(value = HttpStatus.OK)
    public CollectionModel<EntityModel<Device>> getAllDevicesByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        List<EntityModel<Device>> devices = deviceRepository.findByCustomerId(customerId).stream().map(deviceModelAssembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(devices, linkTo(methodOn(DeviceController.class).getAllDevicesByCustomerId(customerId)).withSelfRel());
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
