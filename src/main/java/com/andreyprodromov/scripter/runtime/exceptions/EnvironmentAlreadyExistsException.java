package com.andreyprodromov.scripter.runtime.exceptions;

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

}
