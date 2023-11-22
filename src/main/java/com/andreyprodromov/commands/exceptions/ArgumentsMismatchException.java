package com.andreyprodromov.commands.exceptions;

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

    protected ArgumentsMismatchException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
