package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.ArgumentsMismatchException;
import com.andreyprodromov.runtime.EnvironmentConfig;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteCommandTest {

    EnvironmentConfigLoader manager = mock(EnvironmentConfigLoader.class);
    EnvironmentConfig config = mock(EnvironmentConfig.class);

    @Test
    void deletingEnvironment() {
        String[] args = new String[] { "-d", "-env", "env" };
        var command = new DeleteCommand(args, manager);

        when(manager.getConfig()).thenReturn(config);


        command.execute();
        verify(config).deleteEnvironment("env");
    }

    @Test
    void deletingLocalVariable() {
        String[] args = new String[] { "-d", "-lv", "env", "var" };
        var command = new DeleteCommand(args, manager);

        when(manager.getConfig()).thenReturn(config);

        command.execute();
        verify(config).deleteLocalVariable("env", "var");

    }

    @Test
    void deletingGlobalVariable() {
        String[] args = new String[] {"-d", "-gv", "var"};
        var command = new DeleteCommand(args, manager);

        when(manager.getConfig()).thenReturn(config);

        command.execute();
        verify(config).deleteGlobalVariable("var");
    }

    @Test
    void whenCalledWithWrongArgumentCount_thenExcept() {
        String[] args = new String[] { "-d", "-env", "env", "another"};
        var command = new DeleteCommand(args, manager);

        Assertions.assertThrows(
            ArgumentsMismatchException.class,
            command::execute,
            "Command with mismatched arguments should throw exception on execution"
        );
    }

}