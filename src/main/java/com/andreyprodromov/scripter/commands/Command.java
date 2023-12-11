package com.andreyprodromov.scripter.commands;

@FunctionalInterface
public interface Command {

    /**
     * Executes the command
     */
    void execute();

}
