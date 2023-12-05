package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.CommandOptionDoesNotExistException;
import com.andreyprodromov.commands.utils.Util;
import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.runtime.exceptions.EnvironmentDoesNotExistException;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Set;

/**
 * Command for listing values from the user configuration, such as global variables, local variables, scripts, environments.
 */
public final class ListCommand implements Command {

    private static final int COMMAND_TYPE_INDEX = 1;

    private static final int EXPECTED_ARGS_MINIMUM_LENGTH = 2;

    private static final int GLOBAL_VARIABLES_OPTION_EXPECTED_ARGS_LENGTH = 2;
    private static final int LOCAL_VARIABLES_OPTION_EXPECTED_ARGS_LENGTH = 3;
    private static final int ENVIRONMENTS_OPTION_EXPECTED_ARGS_LENGTH = 2;
    private static final int SCRIPT_OPTION_EXPECTED_ARGS_LENGTH = 3;
    private static final int PARSED_SCRIPT_OPTION_EXPECTED_ARGS_MINIMUM_LENGTH = 3;

    private final String[] args;
    private final Parser parser;
    private final EnvironmentConfigLoader environmentConfigLoader;
    private final PrintStream outputStream;

    /**
     * @param args
     *        the arguments of the command
     *
     * @param parser
     *        the parser used for parsing user scripts
     *
     * @param environmentConfigLoader
     *        the config loader responsible for loading/saving the required
     *        {@link com.andreyprodromov.runtime.EnvironmentConfig EnvironmentConfig}
     *
     * @param outputStream
     *        the output stream
     *
     * @throws com.andreyprodromov.commands.exceptions.ArgumentsMismatchException
     *         when created with wrong number of arguments
     */
    public ListCommand(String[] args, Parser parser, EnvironmentConfigLoader environmentConfigLoader, OutputStream outputStream) {
        this.args = args;
        this.parser = parser;
        this.environmentConfigLoader = environmentConfigLoader;
        this.outputStream = new PrintStream(outputStream);

        validate();
    }

    private void validate() {
        Util.assertMinimumLength(EXPECTED_ARGS_MINIMUM_LENGTH, args);

        String commandType = args[COMMAND_TYPE_INDEX];

        switch (commandType) {
            case "-gv", "--global-variables" ->
                Util.assertExactLength(GLOBAL_VARIABLES_OPTION_EXPECTED_ARGS_LENGTH, args);

            case "-lv", "--local-variables" ->
                Util.assertExactLength(LOCAL_VARIABLES_OPTION_EXPECTED_ARGS_LENGTH, args);

            case "-env", "--environments" ->
                Util.assertExactLength(ENVIRONMENTS_OPTION_EXPECTED_ARGS_LENGTH, args);

            case "-s", "--script" ->
                Util.assertExactLength(SCRIPT_OPTION_EXPECTED_ARGS_LENGTH, args);

            case "-ps", "--parsed-script" ->
                Util.assertMinimumLength(PARSED_SCRIPT_OPTION_EXPECTED_ARGS_MINIMUM_LENGTH, args);

            default ->
                throw new CommandOptionDoesNotExistException(
                    "\"%s\" is not an existing option for --list command".formatted(commandType)
                );
        }
    }

    @Override
    public void execute() {
        Util.assertMinimumLength(EXPECTED_ARGS_MINIMUM_LENGTH, args);

        var config = environmentConfigLoader.getConfig();
        String commandType = args[COMMAND_TYPE_INDEX];

        switch (commandType) {
            case "-gv", "--global-variables" -> {
                var variables = config.getAllGlobalVariables();

                outputStream.println("Global variables:");
                variables.forEach(
                    (key, value) -> outputStream.printf("%s: %s%n", key, value)
                );
            }
            case "-lv", "--local-variables" -> {
                String envName = args[COMMAND_TYPE_INDEX + 1];
                var variables = config.getAllLocalVariables(envName);

                outputStream.println("Local Variables for environment \"" + envName + "\":");
                variables.forEach(
                    (key, value) -> outputStream.printf("%s: %s%n", key, value)
                );
            }
            case "-env", "--environments" -> {
                Set<String> env = config.getEnvironments();

                outputStream.println("Environments:");
                env.forEach(outputStream::println);
            }
            case "-s", "--script" -> {
                String environmentName = args[COMMAND_TYPE_INDEX + 1];

                if (!config.getEnvironments().contains(environmentName))
                    throw new EnvironmentDoesNotExistException(
                        "%s does not exist as an environment".formatted(environmentName)
                    );

                var script = config.getScript(environmentName);

                outputStream.printf("Script for \"%s\":%n%s%n", environmentName, script);
            }
            case "-ps", "--parsed-script" -> {
                String environmentName = args[COMMAND_TYPE_INDEX + 1];

                String script = parser.parse(
                    environmentName,
                    Arrays.copyOfRange(args, COMMAND_TYPE_INDEX + 2, args.length)
                );

                outputStream.printf("Parsed script for \"%s\":%n%s%n", environmentName, script);
            }
        }
    }

}
