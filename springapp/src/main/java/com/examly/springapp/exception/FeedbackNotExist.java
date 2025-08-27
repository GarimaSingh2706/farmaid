package com.examly.springapp.exception;

public class FeedbackNotExist extends RuntimeException {
    public FeedbackNotExist(String msg) {
        super(msg);
    }
}
