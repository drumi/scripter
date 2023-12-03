package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.ArgumentsMismatchException;
import com.andreyprodromov.runtime.EnvironmentConfig;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCommandTest {

    EnvironmentConfigLoader manager = mock(EnvironmentConfigLoader.class);
    EnvironmentConfig config = mock(EnvironmentConfig.class);

    @Test
    void creatingEnvironment() {
        String[] args = new String[] { "-c", "env" };
        var command = new CreateCommand(args, manager);

        when(manager.getConfig()).thenReturn(config);

        command.execute();
        verify(config).createEnvironment("env");

    }

    @Test
    void whenCreatedWithWrongArgumentCount_thenExcept() {
        String[] args = new String[] { "-c", "env", "another"};

        Assertions.assertThrows(
            ArgumentsMismatchException.class,
            () -> new CreateCommand(args, manager),
            "Command with mismatched arguments should throw exception on execution"
        );
    }
}