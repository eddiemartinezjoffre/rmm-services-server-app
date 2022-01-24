package com.ninja.rmm.controller;

import com.ninja.rmm.exception.CustomerNotFoundException;
import com.ninja.rmm.model.Customer;
import com.ninja.rmm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Get all customers via http://localhost:8080/customers
     * @return a List of Customer objects
     */
    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    /**
     * Get a single customer via http://localhost:8080/customers/{id}
     * @param customerId
     * @return Customer object
     */
    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId){
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    /**
     * Create a customer via http://localhost:8080/customers
     * @param customer
     * @return Customer object
     */

    @PostMapping("/customers")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Edit an existing customer via http://localhost:8080/customers/{id}
     * @param customerId
     * @param customerRequest
     * @return Customer object
     */
    @PutMapping("/customers/{customerId}")
    public Customer updateCustomer(@PathVariable Long customerId, @Valid @RequestBody Customer customerRequest) {
        return customerRepository.findById(customerId).map(customer -> {
            customer.setName(customerRequest.getName());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    /**
     * Delete an existing customer via http://localhost:8080/customers/{id}
     * @param customerId
     * @return ResponseEntity
     */
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        return customerRepository.findById(customerId).map(customer -> {
            customerRepository.delete(customer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}
