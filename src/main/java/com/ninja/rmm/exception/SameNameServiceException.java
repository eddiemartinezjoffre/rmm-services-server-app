package com.ninja.rmm.exception;

public class SameNameServiceException extends RuntimeException{

    public SameNameServiceException(Long id, String serviceName){
        super("Could not add the service " + serviceName + " to " + id);
    }
}
