package com.example.springboot2.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}