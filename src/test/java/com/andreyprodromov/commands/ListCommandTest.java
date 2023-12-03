package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.ArgumentsMismatchException;
import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.runtime.EnvironmentConfig;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListCommandTest {

    EnvironmentConfigLoader manager = mock(EnvironmentConfigLoader.class);
    EnvironmentConfig config = mock(EnvironmentConfig.class);
    Parser parser = mock(Parser.class);

    OutputStream outputStream = new ByteArrayOutputStream();


    @BeforeEach
    void beforeEach() {
        when(manager.getConfig()).thenReturn(config);
    }

    @Test
    void globalVariablesListCommandTest() {
        String[] args = new String[] { "-l", "-gv" };
        var command = new ListCommand(args, parser, manager, outputStream);

        when(config.getAllGlobalVariables())
            .thenReturn(
            Map.of("global", "globalValue")
        );

        command.execute();

        verify(config, atLeastOnce()).getAllGlobalVariables();
        Assertions.assertTrue(
            outputStream.toString().contains("globalValue"),
            "Should list global variables"
        );
    }

    @Test
    void localVariablesListCommandTest() {
        String[] args = new String[] { "-l", "-lv", "env" };
        var command = new ListCommand(args, parser, manager, outputStream);

        when(config.getAllLocalVariables("env")).thenReturn(
            Map.of("local", "localValue")
        );

        command.execute();

        verify(config, atLeastOnce())
            .getAllLocalVariables("env");

        Assertions.assertTrue(
            outputStream.toString().contains("localValue"),
            "Should list local variables"
        );
    }

    @Test
    void environmentsListCommandTest() {
        String[] args = new String[] { "-l", "-env" };
        var command = new ListCommand(args, parser, manager, outputStream);

        when(config.getEnvironments()).thenReturn(
            Set.of("env")
        );

        command.execute();

        verify(config, atLeastOnce())
            .getEnvironments();

        Assertions.assertTrue(
            outputStream.toString().contains("env"),
            "Should list local environments"
        );
    }

    @Test
    void scriptListCommandTest() {
        String[] args = new String[] { "-l", "-s", "env"};
        var command = new ListCommand(args, parser, manager, outputStream);

        var environments = Set.of("env");

        when(config.getScript("env"))
            .thenReturn("script");

        when(config.getEnvironments())
            .thenReturn(environments);


        command.execute();

        verify(config, atLeastOnce())
            .getScript("env");

        verify(parser, atMost(0))
            .parse(any(), any());

        Assertions.assertTrue(
            outputStream.toString().contains("script"),
            "Should list script"
        );
    }

    @Test
    void parsedScriptListCommandTest() {
        String[] args = new String[] { "-l", "-ps", "env"};
        var command = new ListCommand(args, parser, manager, outputStream);

        when(config.getScript("env"))
            .thenReturn("script");

        when(parser.parse(eq("env"), any()))
            .thenReturn("parsed script");

        command.execute();

        verify(parser, atLeastOnce())
            .parse(any(), any());

        Assertions.assertTrue(
            outputStream.toString().contains("parsed script"),
            "Should list parsed script"
        );
    }

    @Test
    void whenCreatedWithWrongArgumentCount_thenExcept() {
        String[] args = new String[] { "-l" };

        Assertions.assertThrows(
            ArgumentsMismatchException.class,
            () -> new ListCommand(args, parser, manager, outputStream),
            "Command with mismatched arguments should throw exception on execution"
        );
    }

}