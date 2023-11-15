package com.andreyprodromov.commands.factories;

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
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;

import java.io.OutputStream;

public class DefaultCommandFactory implements CommandFactory {

    private final RuntimeConfigManager runtimeConfigManager;
    private final Parser parser;
    private final OutputStream outputStream;
    private final Executor executor;

    public DefaultCommandFactory(RuntimeConfigManager runtimeConfigManager, Parser parser,
                                 OutputStream outputStream, Executor executor) {
        this.runtimeConfigManager = runtimeConfigManager;
        this.parser = parser;
        this.outputStream = outputStream;
        this.executor = executor;
    }

    @Override
    public Command create(String[] args) {
        if (args.length == 0)
            return new HelpCommand(args, outputStream);

        final int COMMAND_INDEX = 0;
        String command = args[COMMAND_INDEX];

        return switch (command) {
            case "-l", "--list" -> new ListCommand(args, parser, runtimeConfigManager, outputStream);
            case "-e", "--execute" -> new ExecuteCommand(args, parser, executor);
            case "-c", "--create" -> new CreateCommand(args, runtimeConfigManager);
            case "-d", "--delete" -> new DeleteCommand(args, runtimeConfigManager);
            case "-m", "--modify" -> new ModifyCommand(args, runtimeConfigManager);
            case "-h", "--help" -> new HelpCommand(args, outputStream);
            default -> throw new CommandDoesNotExistException(
                String.format("\"%s\" is not an existing command", command)
            );
        };
    }
}
