package com.andreyprodromov.scripter.runtime.exceptions;

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

}
