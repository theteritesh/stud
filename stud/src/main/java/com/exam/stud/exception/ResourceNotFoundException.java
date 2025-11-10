package com.exam.stud.exception;

// This is thrown when a student (or exam, etc.) is not found
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}