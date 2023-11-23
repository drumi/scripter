package com.andreyprodromov.commands;

import com.andreyprodromov.commands.utils.Util;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;

public final class CreateCommand implements Command {

    private static final int ENVIRONMENT_NAME_INDEX = 1;
    private static final int ARGS_EXPECTED_LENGTH = 2;

    private final String[] args;
    private final EnvironmentConfigLoader environmentConfigLoader;

    public CreateCommand(String[] args, EnvironmentConfigLoader environmentConfigLoader) {
        this.args = args;
        this.environmentConfigLoader = environmentConfigLoader;
    }

    @Override
    public void execute() {
        Util.assertExactLength(ARGS_EXPECTED_LENGTH, args);

        environmentConfigLoader.getConfig()
                               .createEnvironment(args[ENVIRONMENT_NAME_INDEX]);

        environmentConfigLoader.saveConfig();
    }

}
