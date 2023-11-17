package com.andreyprodromov.commands;

import com.andreyprodromov.runtime.RuntimeConfig;
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCommandTest {

    RuntimeConfigManager manager = mock(RuntimeConfigManager.class);
    RuntimeConfig config = mock(RuntimeConfig.class);

    @Test
    void creatingEnvironment() {
        String[] args = new String[] { "-c", "env" };
        var command = new CreateCommand(args, manager);

        when(manager.getConfig()).thenReturn(config);

        command.execute();
        verify(config).createEnvironment("env");

    }
}