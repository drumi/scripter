package com.andreyprodromov.scripter.commands.factories;

import com.andreyprodromov.scripter.commands.Command;

@FunctionalInterface
public interface CommandFactory {

    /**
     * @param args
     *        the arguments to be used for {@link com.andreyprodromov.scripter.commands.Command Command} creation
     *
     * @return the corresponding {@code Command} created from the provided arguments
     *
     * @throws com.andreyprodromov.scripter.commands.exceptions.CommandDoesNotExistException
     *         when attempting to create a {@code Command} that does not exist.
     */
    Command create(String[] args);

}
