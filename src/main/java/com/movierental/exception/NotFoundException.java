package com.movierental.exception;

/**
 * Thrown when an expected entity cannot be found.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}