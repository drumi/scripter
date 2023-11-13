package com.andreyprodromov.commands;

import com.andreyprodromov.environment.loaders.ConfigManager;

public final class DeleteCommand implements Command {

    private static final int ENVIRONMENT_NAME_INDEX = 1;

    private final String[] args;

    public DeleteCommand(String[] args) {
        this.args = args;
    }

    @Override
    public void execute() {
        ConfigManager.get()
                     .getConfig()
                     .deleteEnvironment(args[ENVIRONMENT_NAME_INDEX]);

        ConfigManager.get()
                     .saveConfig();
    }

}
