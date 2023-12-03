package com.andreyprodromov.commands;

import com.andreyprodromov.commands.utils.Util;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;


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
     * @param args the arguments of the command
     * @param environmentConfigLoader the config loader responsible for loading/saving the required
     *                                {@link com.andreyprodromov.runtime.EnvironmentConfig EnvironmentConfig}
     *
     * @throws com.andreyprodromov.commands.exceptions.ArgumentsMismatchException when created with wrong number of arguments
     */
    public CloneCommand(String[] args, EnvironmentConfigLoader environmentConfigLoader) {
        this.args = args;
        this.environmentConfigLoader = environmentConfigLoader;

        validate();
    }

    private void validate() {
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

        String script = config.getScript(originalEnvironment);
        if (script != null)
            config.setScript(clonedEnvironment, script);

        environmentConfigLoader.saveConfig();
    }

}
