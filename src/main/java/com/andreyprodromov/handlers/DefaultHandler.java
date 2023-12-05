package com.andreyprodromov.handlers;

import com.andreyprodromov.commands.factories.CommandFactory;

/**
 * The default handler for handling user input.
 */
public final class DefaultHandler implements Handler {

    private final CommandFactory commandFactory;

    /**
     * @param commandFactory
     *        the factory used for {@link com.andreyprodromov.commands.Command Command} creation
     */
    public DefaultHandler(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void handle(String[] args) {
        commandFactory.create(args)
                      .execute();
    }

}
