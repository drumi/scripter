package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.CommandDoesNotExistException;
import com.andreyprodromov.environment.loaders.ConfigManager;
import com.andreyprodromov.parser.Parser;

import java.util.Arrays;
import java.util.Set;

public final class ListCommand implements Command {

    private static final int COMMAND_TYPE_INDEX = 1;

    private final Parser parser = Parser.get();
    private final String args[];

    public ListCommand(String[] args) {
        this.args = args;
    }

    @Override
    public void execute() {
        var config = ConfigManager.get().getConfig();
        String command = args[COMMAND_TYPE_INDEX];


        switch (command) {
            case "-gv", "--global-variables" -> {
                var variables = config.getAllGlobalVariables();

                System.out.println("Global variables:");
                for (var variable : variables.entrySet()) {
                    System.out.println(variable.getKey() + ": " + variable.getValue());
                }
            }
            case "-lv", "--local-variables" -> {
                String envName = args[COMMAND_TYPE_INDEX + 1];
                var variables = config.getAllLocalVariables(envName);

                System.out.println("Local Variables for environment \"" + envName + "\":");
                for (var variable : variables.entrySet()) {
                    System.out.println(variable.getKey() + ": " + variable.getValue());
                }
            }
            case "-env", "--environments" -> {
                Set<String> env = config.getEnvironments();

                System.out.println("Environments:");
                for (var e : env) {
                    System.out.println(e);
                }
            }
            case "-s", "--script" -> {
                String environmentName = args[COMMAND_TYPE_INDEX + 1];
                var script = config.getScript(environmentName);

                System.out.printf("Script for \"%s\":%n%s%n", environmentName, script);
            }
            case "-ps", "--parsed-script" -> {
                String environmentName = args[COMMAND_TYPE_INDEX + 1];
                String script = parser.parse(
                    environmentName,
                    Arrays.copyOfRange(args, COMMAND_TYPE_INDEX + 2, args.length)
                );

                System.out.printf("Parsed script for \"%s\":%n%s%n", environmentName, script);
            }
            default -> {
                throw new CommandDoesNotExistException(
                    String.format("\"%s\" is not an existing option for --list command", command)
                );
            }
        }
    }

}
