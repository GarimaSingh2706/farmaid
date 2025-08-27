package com.examly.springapp.exception;

public class ApplicationStatusCancelledException extends RuntimeException {
    public ApplicationStatusCancelledException(String message) {
        super(message);
    }
}
