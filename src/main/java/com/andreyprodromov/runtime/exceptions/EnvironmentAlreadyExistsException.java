package com.andreyprodromov.runtime.exceptions;

public class EnvironmentAlreadyExistsException extends RuntimeException {

    public EnvironmentAlreadyExistsException() {
        super();
    }

    public EnvironmentAlreadyExistsException(String message) {
        super(message);
    }

    public EnvironmentAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnvironmentAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected EnvironmentAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
                                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
