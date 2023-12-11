package com.andreyprodromov.scripter.commands;

import com.andreyprodromov.scripter.commands.exceptions.ArgumentsMismatchException;
import com.andreyprodromov.scripter.runtime.EnvironmentConfig;
import com.andreyprodromov.scripter.runtime.loaders.EnvironmentConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CloneCommandTest {

    EnvironmentConfigLoader manager = mock(EnvironmentConfigLoader.class);
    EnvironmentConfig config = mock(EnvironmentConfig.class);

    @Test
    void cloneCommandClonesEnvironmentSuccessfully() {
        String[] args = new String[] { "-cl", "old-env", "new-env" };
        var command = new CloneCommand(args, manager);

        Map<String, String> localVariablesOfOldEnvironment = Map.of(
            "variable1", "value1",
            "variable2", "value2"
        );

        when(manager.getConfig()).thenReturn(config);
        when(config.getAllLocalVariables("old-env")).thenReturn(localVariablesOfOldEnvironment);
        when(config.getScript("old-env")).thenReturn(Optional.of("the script"));

        command.execute();

        verify(config).createEnvironment("new-env");
        verify(config).setLocalVariable("new-env", "variable1", "value1");
        verify(config).setLocalVariable("new-env", "variable2", "value2");
        verify(config).setScript("new-env", "the script");
    }

    @Test
    void whenCreatedWithWrongArgumentCount_thenExcept() {
        String[] args = new String[] { "-cl", "old-env"};

        Assertions.assertThrows(
            ArgumentsMismatchException.class,
            () -> new CloneCommand(args, manager),
            "Command with mismatched arguments should throw exception on execution"
        );
    }

}