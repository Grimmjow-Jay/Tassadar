package com.jay.tassadar.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public static void assertTrue(String message) {
        throw new BusinessException(message);
    }

    public static void assertTrue(boolean condition, String message) {
        if (condition) {
            throw new BusinessException(message);
        }
    }
}
