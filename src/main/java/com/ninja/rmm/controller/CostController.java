package com.ninja.rmm.controller;

import com.ninja.rmm.model.Cost;
import com.ninja.rmm.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CostController {

    @Autowired
    private CostService costService;

    /**
     * list monthly cost by a customer via GET http://localhost:8080/customers/{customerId}/cost
     *
     * @param customerId represents the id of the customer
     * @return Total cost for services and devices
     */
    @GetMapping("/customers/{customerId}/cost")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> getCustomerCost(@PathVariable("customerId") Long customerId) {
        Cost cost = costService.costPerCustomer(customerId);
        return new ResponseEntity<>(cost, HttpStatus.OK);
    }

}
