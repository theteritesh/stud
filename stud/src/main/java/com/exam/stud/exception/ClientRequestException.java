package com.exam.stud.exception;

public class ClientRequestException extends RuntimeException {

    public ClientRequestException(String message) {
        super(message);
    }
}