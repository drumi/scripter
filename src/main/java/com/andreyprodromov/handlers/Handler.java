package com.andreyprodromov.handlers;

@FunctionalInterface
public interface Handler {

    /**
     * @param args the arguments to handle
     */
    void handle(String[] args);

}
