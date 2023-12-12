package com.andreyprodromov.scripter.commands;

import com.andreyprodromov.scripter.commands.utils.Util;
import com.andreyprodromov.scripter.parsers.Parser;
import com.andreyprodromov.scripter.platform.Executor;

import java.util.Arrays;
import java.util.Objects;

/**
 * Command for executing the script for a specific environment set in the user configuration.
 */
public final class ExecuteCommand implements Command {

    private static final int ENVIRONMENT_NAME_INDEX = 1;

    private static final int ARGS_LENGTH_MINIMUM = 2;

    private final String[] args;
    private final Parser parser;
    private final Executor executor;

    /**
     * @param args
     *        the arguments of the command
     *
     * @param parser
     *        the parser used for parsing user scripts
     *
     * @param executor
     *        the environment executor
     *
     * @throws com.andreyprodromov.scripter.commands.exceptions.ArgumentsMismatchException
     *         when created with wrong number of arguments
     */
    public ExecuteCommand(String[] args, Parser parser, Executor executor) {
        this.args = Objects.requireNonNull(args, "args must not be null");
        this.parser = Objects.requireNonNull(parser, "parser must not be null");
        this.executor = Objects.requireNonNull(executor, "executor must not be null");

        Util.assertAllNonNull(args);
        Util.assertMinimumLength(ARGS_LENGTH_MINIMUM, args);
    }

    @Override
    public int execute() {
        String environmentName = args[ENVIRONMENT_NAME_INDEX];
        String parsedScript = parser.parse(
            environmentName, Arrays.copyOfRange(args, ENVIRONMENT_NAME_INDEX + 1, args.length)
        );

        return executor.execute(parsedScript);
    }

}
