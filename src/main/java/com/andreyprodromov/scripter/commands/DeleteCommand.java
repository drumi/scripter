package com.andreyprodromov.scripter.commands;

import com.andreyprodromov.scripter.commands.exceptions.CommandOptionDoesNotExistException;
import com.andreyprodromov.scripter.commands.utils.Util;
import com.andreyprodromov.scripter.runtime.loaders.EnvironmentConfigLoader;

import java.util.Objects;

/**
 * Command for deleting values from user configuration, such as global variables, local variables, scripts, environments.
 */
public final class DeleteCommand implements Command {

    private static final int DELETION_TYPE_INDEX = 1;

    private static final int EXPECTED_ARGS_MINIMUM_LENGTH = 3;

    private static final int LOCAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH = 4;
    private static final int GLOBAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH = 3;
    private static final int ENVIRONMENT_OPTION_EXPECTED_ARGS_LENGTH = 3;
    private static final int SCRIPT_OPTION_EXPECTED_ARGS_LENGTH = 3;

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
    public DeleteCommand(String[] args, EnvironmentConfigLoader environmentConfigLoader) {
        this.args = Objects.requireNonNull(args, "args must not be null");
        this.environmentConfigLoader =
            Objects.requireNonNull(environmentConfigLoader, "environmentConfigLoader must not be null");

        Util.assertAllNonNull(args);
        validate();
    }

    private void validate() {
        Util.assertMinimumLength(EXPECTED_ARGS_MINIMUM_LENGTH, args);

        String commandType = args[DELETION_TYPE_INDEX];

        switch (commandType) {
            case "-lv", "--local-variable" ->
                Util.assertExactLength(LOCAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH, args);

            case "-gv", "--global-variable" ->
                Util.assertExactLength(GLOBAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH, args);

            case "-s", "--script" ->
                Util.assertExactLength(SCRIPT_OPTION_EXPECTED_ARGS_LENGTH, args);

            case "-env", "--environment" ->
                Util.assertExactLength(ENVIRONMENT_OPTION_EXPECTED_ARGS_LENGTH, args);

            default ->
                throw new CommandOptionDoesNotExistException(
                    "\"%s\" is not an existing option for --delete command".formatted(commandType)
                );
        }
    }

    @Override
    public void execute() {
        String commandType = args[DELETION_TYPE_INDEX];
        var config = environmentConfigLoader.getConfig();

        switch (commandType) {
            case "-lv", "--local-variable" -> {
                String environmentName = args[DELETION_TYPE_INDEX + 1];
                String variableName = args[DELETION_TYPE_INDEX + 2];
                config.deleteLocalVariable(environmentName, variableName);
            }
            case "-gv", "--global-variable" -> {
                String variableName = args[DELETION_TYPE_INDEX + 1];
                config.deleteGlobalVariable(variableName);
            }
            case "-s", "--script" -> {
                String environment = args[DELETION_TYPE_INDEX + 1];
                config.deleteScript(environment);
            }
            case "-env", "--environment" -> {
                String environment = args[DELETION_TYPE_INDEX + 1];
                config.deleteEnvironment(environment);
            }
        }

        environmentConfigLoader.saveConfig();
    }

}
