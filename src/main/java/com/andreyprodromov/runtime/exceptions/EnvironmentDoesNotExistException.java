package com.andreyprodromov.runtime.exceptions;

public class EnvironmentDoesNotExistException extends RuntimeException {

    public EnvironmentDoesNotExistException() {
        super();
    }

    public EnvironmentDoesNotExistException(String message) {
        super(message);
    }

    public EnvironmentDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnvironmentDoesNotExistException(Throwable cause) {
        super(cause);
    }

    protected EnvironmentDoesNotExistException(String message, Throwable cause, boolean enableSuppression,
                                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
