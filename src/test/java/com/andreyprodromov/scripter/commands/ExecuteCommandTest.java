package com.andreyprodromov.scripter.commands;

import com.andreyprodromov.scripter.commands.exceptions.ArgumentsMismatchException;
import com.andreyprodromov.scripter.parsers.Parser;
import com.andreyprodromov.scripter.platform.Executor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExecuteCommandTest {

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
    void whenCreatedWithWrongArgumentCount_thenExcept() {
        String[] args = new String[] { "-e" };

        Assertions.assertThrows(
            ArgumentsMismatchException.class,
            () -> new ExecuteCommand(args, parser, executor),
            "Command with mismatched arguments should throw exception on execution"
        );
    }

}