package com.andreyprodromov.commands.factories;

import com.andreyprodromov.commands.CloneCommand;
import com.andreyprodromov.commands.Command;
import com.andreyprodromov.commands.CreateCommand;
import com.andreyprodromov.commands.DeleteCommand;
import com.andreyprodromov.commands.ExecuteCommand;
import com.andreyprodromov.commands.HelpCommand;
import com.andreyprodromov.commands.ListCommand;
import com.andreyprodromov.commands.ModifyCommand;
import com.andreyprodromov.commands.exceptions.CommandDoesNotExistException;
import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.platform.Executor;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;

import java.io.OutputStream;

public class DefaultCommandFactory implements CommandFactory {

    private final EnvironmentConfigLoader environmentConfigLoader;
    private final Parser parser;
    private final OutputStream outputStream;
    private final Executor executor;

    public DefaultCommandFactory(EnvironmentConfigLoader environmentConfigLoader, Parser parser,
                                 OutputStream outputStream, Executor executor) {
        this.environmentConfigLoader = environmentConfigLoader;
        this.parser = parser;
        this.outputStream = outputStream;
        this.executor = executor;
    }

    @Override
    public Command create(String[] args) {
        if (args.length == 0)
            return new HelpCommand(outputStream);

        final int COMMAND_INDEX = 0;
        String command = args[COMMAND_INDEX];

        return switch (command) {
            case "-l", "--list" -> new ListCommand(args, parser, environmentConfigLoader, outputStream);
            case "-e", "--execute" -> new ExecuteCommand(args, parser, executor);
            case "-c", "--create" -> new CreateCommand(args, environmentConfigLoader);
            case "-cl", "--clone" -> new CloneCommand(args, environmentConfigLoader);
            case "-d", "--delete" -> new DeleteCommand(args, environmentConfigLoader);
            case "-m", "--modify" -> new ModifyCommand(args, environmentConfigLoader);
            case "-h", "--help" -> new HelpCommand(outputStream);
            default -> throw new CommandDoesNotExistException(
                "\"%s\" is not an existing command".formatted(command)
            );
        };
    }
}
