package com.andreyprodromov.scripter.commands.exceptions;

/**
 * This should be thrown when
 * {@link com.andreyprodromov.scripter.commands.factories.CommandFactory#create(String[]) CommandFactory.create(String[])}
 * is called with a non-existent command.
 */
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

}
