package com.borikov.task5.exception;

public class IncorrectDataException extends Exception {
    public IncorrectDataException() {
    }

    public IncorrectDataException(String message) {
        super(message);
    }

    public IncorrectDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectDataException(Throwable cause) {
        super(cause);
    }
}
