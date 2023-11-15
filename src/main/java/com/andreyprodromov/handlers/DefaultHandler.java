package com.andreyprodromov.handlers;

import com.andreyprodromov.commands.factories.CommandFactory;

public final class DefaultHandler implements Handler {

    private final CommandFactory commandFactory;

    public DefaultHandler(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void handle(String[] args) {
        commandFactory.create(args)
                      .execute();
    }

}
