package com.andreyprodromov.scripter.commands.factories;

import com.andreyprodromov.scripter.commands.CloneCommand;
import com.andreyprodromov.scripter.commands.Command;
import com.andreyprodromov.scripter.commands.CreateCommand;
import com.andreyprodromov.scripter.commands.DeleteCommand;
import com.andreyprodromov.scripter.commands.ExecuteCommand;
import com.andreyprodromov.scripter.commands.HelpCommand;
import com.andreyprodromov.scripter.commands.ListCommand;
import com.andreyprodromov.scripter.commands.ModifyCommand;
import com.andreyprodromov.scripter.commands.VersionCommand;
import com.andreyprodromov.scripter.commands.exceptions.CommandDoesNotExistException;
import com.andreyprodromov.scripter.commands.utils.Util;
import com.andreyprodromov.scripter.parsers.Parser;
import com.andreyprodromov.scripter.platform.Executor;
import com.andreyprodromov.scripter.runtime.loaders.EnvironmentConfigLoader;

import java.io.OutputStream;
import java.util.Objects;


/**
 * The default factory for creating objects implementing
 * {@link com.andreyprodromov.scripter.commands.Command} interface.
 */
public final class DefaultCommandFactory implements CommandFactory {

    private final EnvironmentConfigLoader environmentConfigLoader;
    private final Parser parser;
    private final OutputStream outputStream;
    private final Executor executor;

    /**
     * @param environmentConfigLoader
     *        the config loader responsible for loading/saving the required
     *        {@link com.andreyprodromov.scripter.runtime.EnvironmentConfig EnvironmentConfig}
     *
     * @param parser
     *        the parser used for parsing user scripts
     *
     * @param outputStream
     *        the output stream
     *
     * @param executor
     *        the environment executor
     */
    public DefaultCommandFactory(EnvironmentConfigLoader environmentConfigLoader, Parser parser,
                                 OutputStream outputStream, Executor executor) {
        this.environmentConfigLoader = environmentConfigLoader;
        this.parser = parser;
        this.outputStream = outputStream;
        this.executor = executor;
    }

    @Override
    public Command create(String[] args) {
        Util.assertAllNonNull(args);

        if (args.length == 0)
            return new HelpCommand(outputStream);

        final int commandIndex = 0;
        String command = args[commandIndex];

        return switch (command) {
            case "-l", "--list" -> new ListCommand(args, parser, environmentConfigLoader, outputStream);
            case "-e", "--execute" -> new ExecuteCommand(args, parser, executor);
            case "-c", "--create" -> new CreateCommand(args, environmentConfigLoader);
            case "-cl", "--clone" -> new CloneCommand(args, environmentConfigLoader);
            case "-d", "--delete" -> new DeleteCommand(args, environmentConfigLoader);
            case "-m", "--modify" -> new ModifyCommand(args, environmentConfigLoader);
            case "-h", "--help" -> new HelpCommand(outputStream);
            case "-v", "--version" -> new VersionCommand(outputStream);
            default -> throw new CommandDoesNotExistException(
                "\"%s\" is not an existing command".formatted(command)
            );
        };
    }
}
