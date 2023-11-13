package com.andreyprodromov.commands.exceptions;

public class CommandDoesNotExistException extends RuntimeException {

    public CommandDoesNotExistException() {
        super();
    }

    public CommandDoesNotExistException(String message) {
        super(message);
    }

    public CommandDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandDoesNotExistException(Throwable cause) {
        super(cause);
    }

    protected CommandDoesNotExistException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
