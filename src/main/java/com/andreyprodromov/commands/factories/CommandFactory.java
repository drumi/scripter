package com.andreyprodromov.commands.factories;

import com.andreyprodromov.commands.Command;

@FunctionalInterface
public interface CommandFactory {

    Command create(String[] args);

}
