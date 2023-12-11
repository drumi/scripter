package com.andreyprodromov.scripter.parsers.exceptions;

public class ScriptDoesNotExistException extends RuntimeException {

    public ScriptDoesNotExistException() {
        super();
    }

    public ScriptDoesNotExistException(String message) {
        super(message);
    }

    public ScriptDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptDoesNotExistException(Throwable cause) {
        super(cause);
    }

}
