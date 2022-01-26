package com.ninja.rmm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.ninja.rmm.exception.CustomerNotFoundException;
import com.ninja.rmm.model.Customer;
import com.ninja.rmm.model.CustomerModelAssembler;
import com.ninja.rmm.repository.CustomerRepository;
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
public class CustomerController {

    private final CustomerModelAssembler customerAssembler;

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerController(CustomerModelAssembler customerAssembler) {
        this.customerAssembler = customerAssembler;
    }

    /**
     * Get all customers via GET http://localhost:8080/customers
     *
     * @return a List of Customer domain object with links
     */
    @GetMapping("/customers")
    @ResponseStatus(value = HttpStatus.OK)
    public CollectionModel<EntityModel<Customer>> getAllCustomers() {
        List<EntityModel<Customer>> customers = customerRepository.findAll().stream()
            .map(customerAssembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());
    }

    /**
     * Get a single customer via GET http://localhost:8080/customers/{id}
     *
     * @param customerId represents the id of the customer
     * @return Customer domain object with links
     */
    @GetMapping("/customers/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EntityModel<Customer> getCustomer(@PathVariable Long customerId) {

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return customerAssembler.toModel(customer);
    }

    /**
     * Create a customer via POST http://localhost:8080/customers
     *
     * @param customer represents a Customer object passed in the body of the request
     * @return Customer object
     */

    @PostMapping("/customers")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Edit an existing customer PUT via http://localhost:8080/customers/{id}
     *
     * @param customerId      represents the id of the customer
     * @param customerRequest represents the request sent out on the body
     * @return Customer object
     */
    @PutMapping("/customers/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Customer updateCustomer(@PathVariable Long customerId, @Valid @RequestBody Customer customerRequest) {
        return customerRepository.findById(customerId).map(customer -> {
            customer.setName(customerRequest.getName());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    /**
     * Delete an existing customer via DELETE http://localhost:8080/customers/{id}
     *
     * @param customerId represents the id of the customer
     * @return ResponseEntity object
     */
    @DeleteMapping("/customers/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        return customerRepository.findById(customerId).map(customer -> {
            customerRepository.delete(customer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}
