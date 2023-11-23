package com.andreyprodromov.commands.factories;

import com.andreyprodromov.commands.CloneCommand;
import com.andreyprodromov.commands.Command;
import com.andreyprodromov.commands.CreateCommand;
import com.andreyprodromov.commands.DeleteCommand;
import com.andreyprodromov.commands.ExecuteCommand;
import com.andreyprodromov.commands.HelpCommand;
import com.andreyprodromov.commands.ListCommand;
import com.andreyprodromov.commands.ModifyCommand;
import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.platform.Executor;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.OutputStream;
import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class DefaultCommandFactoryTest {

    EnvironmentConfigLoader environmentConfigLoader = mock(EnvironmentConfigLoader.class);
    Parser parser = mock(Parser.class);
    Executor executor = mock(Executor.class);
    OutputStream outputStream = mock(OutputStream.class);

    CommandFactory factory = new DefaultCommandFactory(environmentConfigLoader, parser, outputStream, executor);
    @Test
    void noArgumentsReturnsHelpCommand() {
        String[] args = new String[] {};
        Command command = factory.create(args);

        Assertions.assertInstanceOf(
            HelpCommand.class,
            command
        );
    }

    @Test
    void createHelpCommand() {
        List<String> names = List.of("-h", "--help");
        check(names, HelpCommand.class);
    }

    @Test
    void createCloneCommand() {
        List<String> names = List.of("-cl", "--clone");
        check(names, CloneCommand.class);
    }

    @Test
    void createCreateCommand() {
        List<String> names = List.of("-c", "--create");
        check(names, CreateCommand.class);
    }

    @Test
    void createDeleteCommand() {
        List<String> names = List.of("-d", "--delete");
        check(names, DeleteCommand.class);
    }

    @Test
    void createListCommand() {
        List<String> names = List.of("-l", "--list");
        check(names, ListCommand.class);
    }

    @Test
    void createExecuteCommand() {
        List<String> names = List.of("-e", "--execute");
        check(names, ExecuteCommand.class);
    }

    @Test
    void createModifyCommand() {
        List<String> names = List.of("-m", "--modify");
        check(names, ModifyCommand.class);
    }


    private void check(List<String> names, Class<?> clazz) {
        for (var name : names) {
            var args = new String[] { name };
            Command command = factory.create(args);

            Assertions.assertInstanceOf(
                clazz,
                command
            );
        }

    }
}