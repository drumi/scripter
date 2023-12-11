package com.andreyprodromov.scripter.commands;

import com.andreyprodromov.scripter.commands.utils.Util;
import com.andreyprodromov.scripter.runtime.loaders.EnvironmentConfigLoader;

import java.util.Objects;


/**
 * Command for cloning an environment from user configuration.
 */
public final class CloneCommand implements Command {

    private static final int ORIGINAL_ENVIRONMENT_NAME_INDEX = 1;
    private static final int CLONED_ENVIRONMENT_NAME_INDEX = 2;

    private static final int ARGS_EXPECTED_LENGTH = 3;

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
    public CloneCommand(String[] args, EnvironmentConfigLoader environmentConfigLoader) {
        this.args = Objects.requireNonNull(args, "args must not be null");
        this.environmentConfigLoader =
            Objects.requireNonNull(environmentConfigLoader, "environmentConfigLoader must not be null");

        Util.assertAllNonNull(args);
        Util.assertExactLength(ARGS_EXPECTED_LENGTH, args);
    }

    @Override
    public void execute() {
        String originalEnvironment = args[ORIGINAL_ENVIRONMENT_NAME_INDEX];
        String clonedEnvironment = args[CLONED_ENVIRONMENT_NAME_INDEX];

        var config = environmentConfigLoader.getConfig();

        config.createEnvironment(clonedEnvironment);

        config.getAllLocalVariables(originalEnvironment)
              .forEach((key, value) -> config.setLocalVariable(clonedEnvironment, key, value));

        config.getScript(originalEnvironment)
              .ifPresent(script -> config.setScript(clonedEnvironment, script));

        environmentConfigLoader.saveConfig();
    }

}
