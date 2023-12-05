package com.andreyprodromov.commands;

import com.andreyprodromov.commands.utils.Util;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;

/**
 * Command for creating an environment inside user configuration.
 */
public final class CreateCommand implements Command {

    private static final int ENVIRONMENT_NAME_INDEX = 1;
    private static final int ARGS_EXPECTED_LENGTH = 2;

    private final String[] args;
    private final EnvironmentConfigLoader environmentConfigLoader;

    /**
     * @param args
     *        the arguments of the command
     *
     * @param environmentConfigLoader
     *        the config loader responsible for loading/saving the required
     *        {@link com.andreyprodromov.runtime.EnvironmentConfig EnvironmentConfig}
     *
     * @throws com.andreyprodromov.commands.exceptions.ArgumentsMismatchException
     *         when created with wrong number of arguments
     */
    public CreateCommand(String[] args, EnvironmentConfigLoader environmentConfigLoader) {
        this.args = args;
        this.environmentConfigLoader = environmentConfigLoader;

        validate();
    }

    private void validate() {
        Util.assertExactLength(ARGS_EXPECTED_LENGTH, args);
    }

    @Override
    public void execute() {
        environmentConfigLoader.getConfig()
                               .createEnvironment(args[ENVIRONMENT_NAME_INDEX]);

        environmentConfigLoader.saveConfig();
    }

}
