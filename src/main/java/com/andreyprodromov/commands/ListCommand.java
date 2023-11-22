package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.CommandDoesNotExistException;
import com.andreyprodromov.commands.utils.Util;
import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.runtime.exceptions.EnvironmentDoesNotExistException;
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Set;

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
    private final RuntimeConfigManager runtimeConfigManager;
    private final PrintStream outputStream;

    public ListCommand(String[] args, Parser parser, RuntimeConfigManager runtimeConfigManager, OutputStream outputStream) {
        this.args = args;
        this.parser = parser;
        this.runtimeConfigManager = runtimeConfigManager;
        this.outputStream = new PrintStream(outputStream);
    }

    @Override
    public void execute() {
        Util.assertMinimumLength(EXPECTED_ARGS_MINIMUM_LENGTH, args);

        var config = runtimeConfigManager.getConfig();
        String command = args[COMMAND_TYPE_INDEX];

        switch (command) {
            case "-gv", "--global-variables" -> {
                Util.assertExactLength(GLOBAL_VARIABLES_OPTION_EXPECTED_ARGS_LENGTH, args);

                var variables = config.getAllGlobalVariables();

                outputStream.println("Global variables:");
                variables.forEach(
                    (key, value) -> outputStream.printf("%s: %s%n", key, value)
                );
            }
            case "-lv", "--local-variables" -> {
                Util.assertExactLength(LOCAL_VARIABLES_OPTION_EXPECTED_ARGS_LENGTH, args);

                String envName = args[COMMAND_TYPE_INDEX + 1];
                var variables = config.getAllLocalVariables(envName);

                outputStream.println("Local Variables for environment \"" + envName + "\":");
                variables.forEach(
                    (key, value) -> outputStream.printf("%s: %s%n", key, value)
                );
            }
            case "-env", "--environments" -> {
                Util.assertExactLength(ENVIRONMENTS_OPTION_EXPECTED_ARGS_LENGTH, args);

                Set<String> env = config.getEnvironments();

                outputStream.println("Environments:");
                env.forEach(outputStream::println);
            }
            case "-s", "--script" -> {
                Util.assertExactLength(SCRIPT_OPTION_EXPECTED_ARGS_LENGTH, args);

                String environmentName = args[COMMAND_TYPE_INDEX + 1];

                if (!config.getEnvironments().contains(environmentName))
                    throw new EnvironmentDoesNotExistException(
                        "%s does not exist as an environment".formatted(environmentName)
                    );

                var script = config.getScript(environmentName);

                outputStream.printf("Script for \"%s\":%n%s%n", environmentName, script);
            }
            case "-ps", "--parsed-script" -> {
                Util.assertMinimumLength(PARSED_SCRIPT_OPTION_EXPECTED_ARGS_MINIMUM_LENGTH, args);

                String environmentName = args[COMMAND_TYPE_INDEX + 1];
                String script = parser.parse(
                    environmentName,
                    Arrays.copyOfRange(args, COMMAND_TYPE_INDEX + 2, args.length)
                );

                outputStream.printf("Parsed script for \"%s\":%n%s%n", environmentName, script);
            }
            default -> {
                throw new CommandDoesNotExistException(
                    "\"%s\" is not an existing option for --list command".formatted(command)
                );
            }
        }
    }

}
