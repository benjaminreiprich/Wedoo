package com.example.exception;

public class EmptyDistributionException extends Exception{

    public EmptyDistributionException() {
        super();
    }

    public EmptyDistributionException(String message) {
        super(message);
    }
}
