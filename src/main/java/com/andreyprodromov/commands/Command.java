package com.andreyprodromov.commands;

@FunctionalInterface
public interface Command {

    /**
     * Executes the command
     */
    void execute();

}
