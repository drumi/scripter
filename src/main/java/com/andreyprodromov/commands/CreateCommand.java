package com.andreyprodromov.commands;

import com.andreyprodromov.environment.loaders.ConfigManager;

public final class CreateCommand implements Command {

    private static final int ENVIRONMENT_NAME_INDEX = 1;

    private final String args[];

    public CreateCommand(String[] args) {
        this.args = args;
    }

    @Override
    public void execute() {
        ConfigManager.get()
                     .getConfig()
                     .createEnvironment(args[ENVIRONMENT_NAME_INDEX]);

        ConfigManager.get()
                     .saveConfig();
    }

}
