package com.andreyprodromov.commands;

import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.platform.Executor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public final class ExecuteCommand implements Command {

    private static final int ENVIRONMENT_NAME_INDEX = 1;

    private final String[] args;
    private final Parser parser;
    private final Executor executor;

    public ExecuteCommand(String[] args, Parser parser, Executor executor) {
        this.args = args;
        this.parser = parser;
        this.executor = executor;
    }

    @Override
    public void execute() {
        String environmentName = args[ENVIRONMENT_NAME_INDEX];
        String parsedScript = parser.parse(
            environmentName, Arrays.copyOfRange(args, ENVIRONMENT_NAME_INDEX + 1, args.length)
        );

        executor.execute(parsedScript);
    }

}
