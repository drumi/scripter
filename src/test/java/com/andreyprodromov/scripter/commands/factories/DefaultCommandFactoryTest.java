package com.andreyprodromov.scripter.commands.factories;

import com.andreyprodromov.scripter.commands.CloneCommand;
import com.andreyprodromov.scripter.commands.Command;
import com.andreyprodromov.scripter.commands.CreateCommand;
import com.andreyprodromov.scripter.commands.DeleteCommand;
import com.andreyprodromov.scripter.commands.ExecuteCommand;
import com.andreyprodromov.scripter.commands.HelpCommand;
import com.andreyprodromov.scripter.commands.ListCommand;
import com.andreyprodromov.scripter.commands.ModifyCommand;
import com.andreyprodromov.scripter.parsers.Parser;
import com.andreyprodromov.scripter.platform.Executor;
import com.andreyprodromov.scripter.runtime.loaders.EnvironmentConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.OutputStream;
import java.util.ArrayList;
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
        check(names, List.of(), HelpCommand.class);
    }

    @Test
    void createCloneCommand() {
        List<String> names = List.of("-cl", "--clone");
        List<String> arguments = List.of("env1", "env2");
        check(names, arguments, CloneCommand.class);
    }

    @Test
    void createCreateCommand() {
        List<String> names = List.of("-c", "--create");
        List<String> arguments = List.of("env");
        check(names, arguments, CreateCommand.class);
    }

    @Test
    void createDeleteCommand() {
        List<String> names = List.of("-d", "--delete");
        List<String> arguments = List.of("-env", "env");
        check(names, arguments, DeleteCommand.class);
    }

    @Test
    void createListCommand() {
        List<String> names = List.of("-l", "--list");
        List<String> arguments = List.of("-env");
        check(names, arguments, ListCommand.class);
    }

    @Test
    void createExecuteCommand() {
        List<String> names = List.of("-e", "--execute");
        List<String> arguments = List.of("env");
        check(names, arguments, ExecuteCommand.class);
    }

    @Test
    void createModifyCommand() {
        List<String> names = List.of("-m", "--modify");
        List<String> arguments = List.of("-ss", "env", "script");
        check(names, arguments, ModifyCommand.class);
    }


    private void check(List<String> names, List<String> arguments, Class<?> clazz) {
        for (var name : names) {
            List<String> tmp = new ArrayList<>(arguments);
            tmp.add(0, name);
            String[] args = tmp.toArray(String[]::new);

            Command command = factory.create(args);

            Assertions.assertInstanceOf(
                clazz,
                command
            );
        }

    }
}