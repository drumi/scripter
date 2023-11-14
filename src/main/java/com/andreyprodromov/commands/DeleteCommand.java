package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.CommandDoesNotExistException;
import com.andreyprodromov.environment.loaders.ConfigManager;

public final class DeleteCommand implements Command {

    private static final int DELETION_TYPE_INDEX = 1;

    private final String[] args;

    public DeleteCommand(String[] args) {
        this.args = args;
    }

    @Override
    public void execute() {
        String command = args[DELETION_TYPE_INDEX];
        var config = ConfigManager.get().getConfig();

        switch (command) {
            case "-lv", "--local-variable" -> {
                String environmentName = args[DELETION_TYPE_INDEX + 1];
                String variableName = args[DELETION_TYPE_INDEX + 2];
                config.deleteLocalVariable(environmentName, variableName);
            }
            case "-gv", "--global-variable" -> {
                String variableName = args[DELETION_TYPE_INDEX + 1];

                config.deleteGlobalVariable(variableName);
            }
            case "-env", "--environment" -> {
                String environment = args[DELETION_TYPE_INDEX + 1];

                config.deleteEnvironment(environment);
            }
            default -> {
                throw new CommandDoesNotExistException(
                    String.format("\"%s\" is not an existing option for --delete command", command)
                );
            }
        }

        ConfigManager.get()
                     .saveConfig();
    }

}
