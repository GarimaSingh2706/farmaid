package com.examly.springapp.exception;

public class DuplicateLoanException extends RuntimeException {
    public DuplicateLoanException(String message) {
        super(message);
    }
}
