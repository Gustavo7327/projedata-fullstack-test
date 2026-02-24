package com.br.factory.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String resourceName, String identifier) {
        super(
            String.format("%s with identifier '%s' not found", resourceName, identifier),
            HttpStatus.NOT_FOUND
        );
    }
}
