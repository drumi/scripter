package com.andreyprodromov.commands;

import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.platform.Executor;
import com.andreyprodromov.runtime.RuntimeConfig;
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExecuteCommandTest {

    RuntimeConfigManager manager = mock(RuntimeConfigManager.class);
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

}