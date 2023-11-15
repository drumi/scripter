package com.andreyprodromov.commands;

import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;

public final class CreateCommand implements Command {

    private static final int ENVIRONMENT_NAME_INDEX = 1;

    private final String args[];
    private final RuntimeConfigManager runtimeConfigManager;

    public CreateCommand(String[] args, RuntimeConfigManager runtimeConfigManager) {
        this.args = args;
        this.runtimeConfigManager = runtimeConfigManager;
    }

    @Override
    public void execute() {
        runtimeConfigManager.getConfig()
                            .createEnvironment(args[ENVIRONMENT_NAME_INDEX]);

        runtimeConfigManager.saveConfig();
    }

}
