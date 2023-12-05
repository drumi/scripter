package com.andreyprodromov.commands.factories;

import com.andreyprodromov.commands.Command;

@FunctionalInterface
public interface CommandFactory {

    /**
     * @param args
     *        the arguments to be used for {@link com.andreyprodromov.commands.Command Command} creation
     *
     * @return the corresponding {@code Command} created from the provided arguments
     *
     * @throws com.andreyprodromov.commands.exceptions.CommandDoesNotExistException
     *         when attempting to create a {@code Command} that does not exist.
     */
    Command create(String[] args);

}
