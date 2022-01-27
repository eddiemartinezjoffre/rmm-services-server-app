package com.ninja.rmm.exception;

public class ServiceNotFoundException extends RuntimeException {

  public ServiceNotFoundException(Long id) {
    super("Could not find service " + id);
  }
}
