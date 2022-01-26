package com.ninja.rmm.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ninja.rmm.controller.ServiceController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ServiceModelAssembler implements RepresentationModelAssembler<Service, EntityModel<Service>> {

  @Override
  public EntityModel<Service> toModel(Service service){

    return EntityModel.of(service,
        linkTo(methodOn(ServiceController.class).getAllServicesByCustomerId(service.getCustomer().getId())).withSelfRel());
  }
}