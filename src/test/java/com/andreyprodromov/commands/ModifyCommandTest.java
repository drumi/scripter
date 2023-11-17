package com.andreyprodromov.commands;

import com.andreyprodromov.runtime.RuntimeConfig;
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ModifyCommandTest {

    RuntimeConfigManager manager = mock(RuntimeConfigManager.class);
    RuntimeConfig config = mock(RuntimeConfig.class);

    @BeforeEach
    void beforeEach() {
        when(manager.getConfig()).thenReturn(config);
    }
    
    @Test
    void setGlobalVariable() {
        String[] args = new String[] { "-m", "-sgv", "global", "value"};
        var command = new ModifyCommand(args, manager);

        command.execute();

        verify(config).setGlobalVariable("global", "value");
    }

    @Test
    void setLocalVariable() {
        String[] args = new String[] { "-m", "-slv", "env", "local", "value"};
        var command = new ModifyCommand(args, manager);

        command.execute();

        verify(config).setLocalVariable("env", "local", "value");
    }

    @Test
    void setScript() throws IOException {
        File file = File.createTempFile("script", "txt");
        file.deleteOnExit();
        Files.writeString(file.toPath(), "script in file");

        String[] args = new String[] { "-m", "-ss", "env", file.toString()};
        var command = new ModifyCommand(args, manager);

        command.execute();

        verify(config).setScript("env", "script in file");
    }
}