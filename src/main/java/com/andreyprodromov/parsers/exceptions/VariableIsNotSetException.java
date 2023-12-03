package com.andreyprodromov.parsers.exceptions;

public class VariableIsNotSetException extends RuntimeException {

    public VariableIsNotSetException() {
        super();
    }

    public VariableIsNotSetException(String message) {
        super(message);
    }

    public VariableIsNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public VariableIsNotSetException(Throwable cause) {
        super(cause);
    }

}
