package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.CommandDoesNotExistException;
import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Set;

public final class ListCommand implements Command {

    private static final int COMMAND_TYPE_INDEX = 1;

    private final String args[];
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
        var config = runtimeConfigManager.getConfig();
        String command = args[COMMAND_TYPE_INDEX];


        switch (command) {
            case "-gv", "--global-variables" -> {
                var variables = config.getAllGlobalVariables();

                outputStream.println("Global variables:");
                for (var variable : variables.entrySet()) {
                    outputStream.println(variable.getKey() + ": " + variable.getValue());
                }
            }
            case "-lv", "--local-variables" -> {
                String envName = args[COMMAND_TYPE_INDEX + 1];
                var variables = config.getAllLocalVariables(envName);

                outputStream.println("Local Variables for environment \"" + envName + "\":");
                for (var variable : variables.entrySet()) {
                    outputStream.println(variable.getKey() + ": " + variable.getValue());
                }
            }
            case "-env", "--environments" -> {
                Set<String> env = config.getEnvironments();

                outputStream.println("Environments:");
                for (var e : env) {
                    outputStream.println(e);
                }
            }
            case "-s", "--script" -> {
                String environmentName = args[COMMAND_TYPE_INDEX + 1];
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
            default -> {
                throw new CommandDoesNotExistException(
                    String.format("\"%s\" is not an existing option for --list command", command)
                );
            }
        }
    }

}
