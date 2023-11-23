package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.ArgumentsMismatchException;
import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.platform.Executor;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExecuteCommandTest {

    EnvironmentConfigLoader manager = mock(EnvironmentConfigLoader.class);
    Parser parser = mock(Parser.class);
    Executor executor = mock(Executor.class);

    @Test
    void executingCommand() {
        String[] args = new String[] { "-e", "env", "arg1"};
        var command = new ExecuteCommand(args, parser, executor);

        when(parser.parse(any(), any())).thenReturn("parsed script");

        command.execute();

        verify(parser).parse("env", new String[]{ "arg1" });
        verify(executor).execute("parsed script");
    }

    @Test
    void whenCalledWithWrongArgumentCount_thenExcept() {
        String[] args = new String[] { "-e" };
        var command = new ExecuteCommand(args, parser, executor);

        Assertions.assertThrows(
            ArgumentsMismatchException.class,
            command::execute,
            "Command with mismatched arguments should throw exception on execution"
        );
    }

}