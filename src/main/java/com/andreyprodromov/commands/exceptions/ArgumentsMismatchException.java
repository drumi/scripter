package com.andreyprodromov.commands.exceptions;

/**
 * This should be thrown when a
 * {@link com.andreyprodromov.commands.Command Command}
 * is created with wrong number of arguments.
 */
public class ArgumentsMismatchException extends RuntimeException {

    public ArgumentsMismatchException() {
        super();
    }

    public ArgumentsMismatchException(String message) {
        super(message);
    }

    public ArgumentsMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentsMismatchException(Throwable cause) {
        super(cause);
    }

}
