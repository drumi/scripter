package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.ArgumentsMismatchException;
import com.andreyprodromov.runtime.EnvironmentConfig;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;
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

    EnvironmentConfigLoader manager = mock(EnvironmentConfigLoader.class);
    EnvironmentConfig config = mock(EnvironmentConfig.class);

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

    @Test
    void whenCalledWithWrongArgumentCount_thenExcept() {
        String[] args = new String[] { "-m", "-ss", "env" };
        var command = new ModifyCommand(args, manager);

        Assertions.assertThrows(
            ArgumentsMismatchException.class,
            command::execute,
            "Command with mismatched arguments should throw exception on execution"
        );
    }
}