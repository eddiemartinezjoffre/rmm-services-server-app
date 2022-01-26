package com.ninja.rmm.controller;

import com.ninja.rmm.exception.CustomerNotFoundException;
import com.ninja.rmm.exception.ServiceNotFoundException;
import com.ninja.rmm.model.Service;
import com.ninja.rmm.repository.CustomerRepository;
import com.ninja.rmm.repository.ServiceRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ServiceController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * lis all services assigned to a customer via GET http://localhost:8080/customers/{customerId}/services
     *
     * @param customerId represents the id of the customer
     * @return Lis of Service objects
     */
    @GetMapping("/customers/{customerId}/services")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Service> getAllServicesByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        return serviceRepository.findByCustomerId(customerId);
    }

    /**
     * Create a service and assign to a customer via POST http://localhost:8080/customers/{customerId}/services
     *
     * @param customerId represents the id of the customer
     * @param service    Service object been passed via request body
     * @return Service object
     */
    @PostMapping("/customers/{customerId}/services")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Service createService(@PathVariable(value = "customerId") Long customerId,
                                 @Valid @RequestBody Service service) {
        return customerRepository.findById(customerId).map(customer -> {
            service.setCustomer(customer);
            return serviceRepository.save(service);
        }).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    /**
     * Update an existing service via PUT http://localhost:8080/customers/{customerId}/services/{serviceId}
     *
     * @param customerId     represents the id of the customer
     * @param serviceId      represents the id of the service
     * @param serviceRequest represents the request object been passed
     * @return Service object
     */
    @PutMapping("/customers/{customerId}/services/{serviceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Service updateService(@PathVariable(value = "customerId") Long customerId,
                                 @PathVariable(value = "serviceId") Long serviceId,
                                 @Valid @RequestBody Service serviceRequest) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        return serviceRepository.findById(serviceId).map(service -> {
            service.setServiceName(serviceRequest.getServiceName());
            service.setServiceDescription(serviceRequest.getServiceDescription());
            service.setServiceCost(serviceRequest.getServiceCost());
            return serviceRepository.save(service);
        }).orElseThrow(() -> new ServiceNotFoundException(serviceId));
    }

    /**
     * Delete a service via DELETE http://localhost:8080/customers/{customerId}/services/{serviceId}
     *
     * @param customerId represents the id of the customer
     * @param serviceId  represents the id of the service
     * @return ResponseEntity
     */
    @DeleteMapping("/customers/{customerId}/services/{serviceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> deleteService(@PathVariable(value = "customerId") Long customerId,
                                           @PathVariable(value = "serviceId") Long serviceId) {
        return serviceRepository.findByIdAndCustomerId(serviceId, customerId).map(service -> {
            serviceRepository.delete(service);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ServiceNotFoundException(serviceId));
    }
}
