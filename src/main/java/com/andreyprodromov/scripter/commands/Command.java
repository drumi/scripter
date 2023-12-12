package com.andreyprodromov.scripter.commands;

@FunctionalInterface
public interface Command {

    /**
     * Executes the command
     *
     * @return the status code after command execution. A zero value means success
     */
    int execute();

}
