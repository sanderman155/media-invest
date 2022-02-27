package com.kinzhitaev.mibackend.security.exception;

public class InvalidJwtTokenException extends RuntimeException {
    private String message;

    public InvalidJwtTokenException() {
        this.message = "Invalid token";
    }

    public InvalidJwtTokenException(String message) {
        this.message = message;
    }
}
