package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.CommandOptionDoesNotExistException;
import com.andreyprodromov.commands.utils.Util;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Command for modifying values from the user configuration, such as global variables, local variables, scripts.
 */
public final class ModifyCommand implements Command {

    private static final int MODIFICATION_TYPE_INDEX = 1;

    private static final int EXPECTED_ARGS_MINIMUM_LENGTH = 4;

    private static final int SET_LOCAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH = 5;
    private static final int SET_GLOBAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH = 4;
    private static final int SET_SCRIPT_OPTION_EXPECTED_ARGS_LENGTH = 4;


    private final String[] args;
    private final EnvironmentConfigLoader environmentConfigLoader;

    /**
     * @param args the arguments of the command
     * @param environmentConfigLoader the config loader responsible for loading/saving the required
     *                                {@link com.andreyprodromov.runtime.EnvironmentConfig EnvironmentConfig}
     *
     * @throws com.andreyprodromov.commands.exceptions.ArgumentsMismatchException when created with wrong number of arguments
     */
    public ModifyCommand(String[] args, EnvironmentConfigLoader environmentConfigLoader) {
        this.args = args;
        this.environmentConfigLoader = environmentConfigLoader;

        validate();
    }

    private void validate() {
        Util.assertMinimumLength(EXPECTED_ARGS_MINIMUM_LENGTH, args);

        String commandType = args[MODIFICATION_TYPE_INDEX];

        switch (commandType) {
            case "-slv", "--set-local-variable" ->
                Util.assertExactLength(SET_LOCAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH, args);

            case "-sgv", "--set-global-variable" ->
                Util.assertExactLength(SET_GLOBAL_VARIABLE_OPTION_EXPECTED_ARGS_LENGTH, args);

            case "-ss", "--set-script" ->
                Util.assertExactLength(SET_SCRIPT_OPTION_EXPECTED_ARGS_LENGTH, args);

            default ->
                throw new CommandOptionDoesNotExistException(
                    "\"%s\" is not an existing option for --modify command".formatted(commandType)
                );
        }
    }

    @Override
    public void execute() {
        String command = args[MODIFICATION_TYPE_INDEX];
        var config = environmentConfigLoader.getConfig();

        switch (command) {
            case "-slv", "--set-local-variable" -> {
                final int environmentIndex = MODIFICATION_TYPE_INDEX + 1;
                final int variableNameIndex = MODIFICATION_TYPE_INDEX + 2;
                final int variableValueIndex = MODIFICATION_TYPE_INDEX + 3;

                config.setLocalVariable(
                    args[environmentIndex],
                    args[variableNameIndex],
                    args[variableValueIndex]
                );
            }
            case "-sgv", "--set-global-variable" -> {
                final int variableNameIndex = MODIFICATION_TYPE_INDEX + 1;
                final int variableValueIndex = MODIFICATION_TYPE_INDEX + 2;

                config.setGlobalVariable(
                    args[variableNameIndex],
                    args[variableValueIndex]
                );
            }
            case "-ss", "--set-script" -> {
                final int environmentIndex = MODIFICATION_TYPE_INDEX + 1;
                final int scriptFileIndex = MODIFICATION_TYPE_INDEX + 2;

                try {
                    String script = Files.readString(Path.of(args[scriptFileIndex]));
                    config.setScript(
                        args[environmentIndex],
                        script
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        environmentConfigLoader.saveConfig();
    }

}
