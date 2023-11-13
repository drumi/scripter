package com.andreyprodromov.commands;

import com.andreyprodromov.environment.loaders.ConfigManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public final class ListCommand implements Command {

    private static final int COMMAND_TYPE_INDEX = 1;
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
        }
    }

}
