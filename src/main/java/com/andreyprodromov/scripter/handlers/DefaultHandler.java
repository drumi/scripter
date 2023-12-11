package com.andreyprodromov.scripter.handlers;

import com.andreyprodromov.scripter.commands.factories.CommandFactory;

import java.util.Objects;

/**
 * The default handler for handling user input.
 */
public final class DefaultHandler implements Handler {

    private final CommandFactory commandFactory;

    /**
     * @param commandFactory
     *        the factory used for {@link com.andreyprodromov.scripter.commands.Command Command} creation
     */
    public DefaultHandler(CommandFactory commandFactory) {
        this.commandFactory = Objects.requireNonNull(commandFactory, "commandFactory must not be null");
    }

    @Override
    public void handle(String[] args) {
        commandFactory.create(args)
                      .execute();
    }

}
