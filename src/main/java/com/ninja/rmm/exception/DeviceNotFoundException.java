package com.ninja.rmm.exception;

public class DeviceNotFoundException extends RuntimeException {

  public DeviceNotFoundException(Long id) {
    super("Could not find device " + id);
  }
}
