package com.andreyprodromov.scripter.commands;

import com.andreyprodromov.scripter.commands.utils.Util;
import com.andreyprodromov.scripter.runtime.loaders.EnvironmentConfigLoader;

import java.util.Objects;

/**
 * Command for creating an environment inside user configuration.
 */
public final class CreateCommand implements Command {

    private static final int EXECUTION_SUCCESS = 0;

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
     *        {@link com.andreyprodromov.scripter.runtime.EnvironmentConfig EnvironmentConfig}
     *
     * @throws com.andreyprodromov.scripter.commands.exceptions.ArgumentsMismatchException
     *         when created with wrong number of arguments
     */
    public CreateCommand(String[] args, EnvironmentConfigLoader environmentConfigLoader) {
        this.args = Objects.requireNonNull(args, "args must not be null");
        this.environmentConfigLoader =
            Objects.requireNonNull(environmentConfigLoader, "environmentConfigLoader must not be null");

        Util.assertAllNonNull(args);
        Util.assertExactLength(ARGS_EXPECTED_LENGTH, args);
    }

    @Override
    public int execute() {
        environmentConfigLoader.getConfig()
                               .createEnvironment(args[ENVIRONMENT_NAME_INDEX]);

        environmentConfigLoader.saveConfig();

        return EXECUTION_SUCCESS;
    }

}
