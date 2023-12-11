package com.andreyprodromov.scripter.commands.exceptions;

/**
 * This should be thrown when
 * {@link com.andreyprodromov.scripter.commands.Command Command}
 * is created with a non-existent option.
 */
public class CommandOptionDoesNotExistException extends RuntimeException {

    public CommandOptionDoesNotExistException() {
        super();
    }

    public CommandOptionDoesNotExistException(String message) {
        super(message);
    }

    public CommandOptionDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandOptionDoesNotExistException(Throwable cause) {
        super(cause);
    }

}
