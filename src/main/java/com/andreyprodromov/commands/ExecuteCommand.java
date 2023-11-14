package com.andreyprodromov.commands;

import com.andreyprodromov.parser.Parser;

import java.io.IOException;
import java.util.Arrays;

public final class ExecuteCommand implements Command {

    private static final int ENVIRONMENT_NAME_INDEX = 1;

    private final Parser parser = Parser.get();
    private final String[] args;

    public ExecuteCommand(String[] args) {
        this.args = args;
    }

    @Override
    public void execute() {
        String environmentName = args[ENVIRONMENT_NAME_INDEX];
        String parsedScript = parser.parse(
            environmentName, Arrays.copyOfRange(args, ENVIRONMENT_NAME_INDEX + 1, args.length)
        );

        // Execute script
        try {
            System.out.println(parsedScript);
            Runtime.getRuntime().exec(parsedScript);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
