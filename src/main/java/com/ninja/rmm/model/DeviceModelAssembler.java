package com.ninja.rmm.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ninja.rmm.controller.DeviceController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DeviceModelAssembler implements RepresentationModelAssembler<Device, EntityModel<Device>> {
  @Override
  public EntityModel<Device> toModel(Device device){

    return EntityModel.of(device,
        linkTo(methodOn(DeviceController.class).getAllDevicesByCustomerId(device.getCustomer().getId())).withSelfRel());
  }
}