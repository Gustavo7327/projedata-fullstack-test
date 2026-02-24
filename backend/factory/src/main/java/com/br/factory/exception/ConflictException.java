package com.br.factory.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public ConflictException(String resourceName, String field, String value) {
        super(
            String.format("%s with %s '%s' already exists", resourceName, field, value),
            HttpStatus.CONFLICT
        );
    }
}