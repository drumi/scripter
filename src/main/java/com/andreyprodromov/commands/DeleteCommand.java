package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.CommandDoesNotExistException;
import com.andreyprodromov.commands.utils.Util;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;

public final class DeleteCommand implements Command {

    private static final int DELETION_TYPE_INDEX = 1;

    private static final int EXPECTED_ARGS_MINIMUM_LENGTH = 3;

    private static final int LOCAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH = 4;
    private static final int GLOBAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH = 3;
    private static final int ENVIRONMENT_OPTION_EXPECTED_ARGS_LENGTH = 3;

    private final String[] args;
    private final EnvironmentConfigLoader environmentConfigLoader;

    public DeleteCommand(String[] args, EnvironmentConfigLoader environmentConfigLoader) {
        this.args = args;
        this.environmentConfigLoader = environmentConfigLoader;
    }

    @Override
    public void execute() {
        Util.assertMinimumLength(EXPECTED_ARGS_MINIMUM_LENGTH, args);

        String command = args[DELETION_TYPE_INDEX];
        var config = environmentConfigLoader.getConfig();

        switch (command) {
            case "-lv", "--local-variable" -> {
                Util.assertExactLength(LOCAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH, args);

                String environmentName = args[DELETION_TYPE_INDEX + 1];
                String variableName = args[DELETION_TYPE_INDEX + 2];
                config.deleteLocalVariable(environmentName, variableName);
            }
            case "-gv", "--global-variable" -> {
                Util.assertExactLength(GLOBAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH, args);

                String variableName = args[DELETION_TYPE_INDEX + 1];

                config.deleteGlobalVariable(variableName);
            }
            case "-env", "--environment" -> {
                Util.assertExactLength(ENVIRONMENT_OPTION_EXPECTED_ARGS_LENGTH, args);

                String environment = args[DELETION_TYPE_INDEX + 1];

                config.deleteEnvironment(environment);
            }
            default -> {
                throw new CommandDoesNotExistException(
                    "\"%s\" is not an existing option for --delete command".formatted(command)
                );
            }
        }

        environmentConfigLoader.saveConfig();
    }

}
