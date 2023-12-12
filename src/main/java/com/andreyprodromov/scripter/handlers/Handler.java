package com.andreyprodromov.scripter.handlers;

@FunctionalInterface
public interface Handler {

    /**
     * @param args
     *        the arguments to handle
     *
     * @return status code after handling user input. A zero value means success
     */
    int handle(String[] args);

}
