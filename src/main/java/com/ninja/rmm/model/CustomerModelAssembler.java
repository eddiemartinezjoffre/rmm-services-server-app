package com.ninja.rmm.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ninja.rmm.controller.CustomerController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {
  @Override
  public EntityModel<Customer> toModel(Customer customer){

    return EntityModel.of(customer,
        linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel(),
        linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
  }
}
