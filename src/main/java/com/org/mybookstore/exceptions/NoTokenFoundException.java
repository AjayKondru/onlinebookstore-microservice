package com.org.mybookstore.exceptions;

public class NoTokenFoundException extends RuntimeException {
    public NoTokenFoundException(String message) {
        super(message);
    }
}
