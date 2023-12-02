package com.andreyprodromov.parsers;

import com.andreyprodromov.runtime.EnvironmentConfig;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Pattern;

import static org.mockito.Mockito.*;
import static org.mockito.AdditionalMatchers.*;


@ExtendWith(MockitoExtension.class)
class DefaultParserTest {

    private static final Pattern variablePattern = Pattern.compile("%\\[(\\S+)]%");

    @Test
    void parsingGeneralScript() {
        String environment = "env";
        String script = "%[globalVar]% --other %[localVar]% %[1]% %[2]%";
        String globalVarValue = "globalValue";
        String localVarValue = "localValue";
        String firstCommandLineArgumentValue = "one";
        String secondCommandLineArgumentValue = "two";
        String[] args = new String[]{ firstCommandLineArgumentValue, secondCommandLineArgumentValue };

        var configRunnerMock = mock(EnvironmentConfigLoader.class);
        var configMock = mock(EnvironmentConfig.class);

        when(configRunnerMock.getConfig()).thenReturn(configMock);
        when(configMock.getGlobalVariable("globalVar")).thenReturn(globalVarValue);
        when(configMock.getLocalVariable(environment, "localVar")).thenReturn(localVarValue);
        when(configMock.getLocalVariable(eq(environment), not(eq("localVar")))).thenReturn(null);
        when(configMock.getScript(environment)).thenReturn(script);

        var parser = new DefaultParser(variablePattern, configRunnerMock);

        String parsedScript = parser.parse(
            environment,
            args
        );

        Assertions.assertEquals(
            "globalValue --other localValue one two",
            parsedScript,
            "Script should parse correctly"
        );
    }

    @Test
    void localVariableShouldShadowGlobalVariable() {
        String environment = "env";
        String script = "%[shadowVar]% --other";
        String globalVarValue = "globalValue";
        String localVarValue = "localValue";
        String[] args = new String[]{};


        var configRunnerMock = mock(EnvironmentConfigLoader.class);
        var configMock = mock(EnvironmentConfig.class);

        when(configRunnerMock.getConfig()).thenReturn(configMock);
        lenient().when(configMock.getGlobalVariable("shadowVar")).thenReturn(globalVarValue);
        lenient().when(configMock.getLocalVariable(environment, "shadowVar")).thenReturn(localVarValue);
        when(configMock.getScript(environment)).thenReturn(script);

        var parser = new DefaultParser(variablePattern, configRunnerMock);

        String parsedScript = parser.parse(
            environment,
            args
        );

        Assertions.assertEquals(
            "localValue --other",
            parsedScript,
            "Local variable should shadow global variable"
        );
    }
}