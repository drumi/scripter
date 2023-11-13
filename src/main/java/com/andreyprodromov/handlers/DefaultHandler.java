package com.andreyprodromov.handlers;

import com.andreyprodromov.commands.Command;

public final class DefaultHandler implements Handler {

    @Override
    public void handle(String[] args) {
        Command.create(args)
               .execute();
    }

}
