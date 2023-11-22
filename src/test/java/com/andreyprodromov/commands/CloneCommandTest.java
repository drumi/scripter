package com.andreyprodromov.commands;

import com.andreyprodromov.runtime.RuntimeConfig;
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CloneCommandTest {

    RuntimeConfigManager manager = mock(RuntimeConfigManager.class);
    RuntimeConfig config = mock(RuntimeConfig.class);

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
        when(config.getScript("old-env")).thenReturn("the script");

        command.execute();

        verify(config).createEnvironment("new-env");
        verify(config).setLocalVariable("new-env", "variable1", "value1");
        verify(config).setLocalVariable("new-env", "variable2", "value2");
        verify(config).setScript("new-env", "the script");
    }

}