package com.exam.stud.exception;

// This is thrown when trying to create a resource that already exists (e.g., duplicate email)
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}