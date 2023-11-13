package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.CommandDoesNotExistException;

@FunctionalInterface
public interface Command {

    void execute();

    static Command create(String[] args) {
        if (args.length == 0)
            return new HelpCommand(args);

        final int COMMAND_INDEX = 0;
        String command = args[COMMAND_INDEX];

        return switch (command) {
            case "-l", "--list" -> new ListCommand(args);
            case "-e", "--execute" -> new ExecuteCommand(args);
            case "-c", "--create" -> new CreateCommand(args);
            case "-d", "--delete" -> new DeleteCommand(args);
            case "-m", "--modify" -> new ModifyCommand(args);
            case "-h", "--help" -> new HelpCommand(args);
            default -> throw new CommandDoesNotExistException(
                String.format("\"%s\" is not an existing command", command)
            );
        };
    }
}
