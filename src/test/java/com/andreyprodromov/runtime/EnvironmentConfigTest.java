package com.andreyprodromov.runtime;

import com.andreyprodromov.runtime.exceptions.EnvironmentDoesNotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

class EnvironmentConfigTest {

    @Test
    void whenEnvironmentIsCreated_thenItIsAddedToConfig() {
        var config = new EnvironmentConfig();
        String environment = "test";

        Assertions.assertFalse(
            config.getEnvironments().contains(environment),
            "EnvironmentConfig should not contain the environment before creating it"
        );

        config.createEnvironment(environment);

        Assertions.assertTrue(
            config.getEnvironments().contains(environment),
            "EnvironmentConfig should contain the environment after creating it"
        );
    }

    @Test
    void whenEnvironmentIsDeleted_thenItIsRemovedFromConfig() {
        var config = new EnvironmentConfig();
        String environment = "test";

        config.createEnvironment(environment);

        Assertions.assertTrue(
            config.getEnvironments().contains(environment),
            "EnvironmentConfig should contain the environment before deleting it"
        );

        config.deleteEnvironment(environment);

        Assertions.assertFalse(
            config.getEnvironments().contains(environment),
            "EnvironmentConfig should not contain the environment after deleting it"
        );
    }

    @Test
    void whenGetEnvironmentsIsCalled_thenReturnsEnvironments() {
        var config = new EnvironmentConfig();
        String env1 = "env1";
        String env2 = "env2";
        String env3 = "env3";

        config.createEnvironment(env1);
        config.createEnvironment(env2);
        config.createEnvironment(env3);

        Assertions.assertEquals(
            Set.of(env1, env2, env3),
            config.getEnvironments(),
            "Correct environments should be returned after creation"
        );
    }

    @Test
    void whenLocalVariableIsCreatedToEnvironmentThatDoesNotExists_thenExceptionIsThrown() {
        var config = new EnvironmentConfig();
        String environment = "Nonexistent";
        String variableName = "name";
        String variableValue = "value";


        Assertions.assertThrows(
            EnvironmentDoesNotExistException.class,
            () -> config.setLocalVariable(environment, variableName, variableValue),
            "Exception should be thrown when trying to set a variable in environment that does not exist"
        );
    }

    @Test
    void whenLocalVariableIsCreatedToEnvironment_thenItIsAddedToTheEnvironment() {
        var config = new EnvironmentConfig();
        String environment = "test";
        String variableName = "name";
        String variableValue = "value";

        config.createEnvironment(environment);

        config.setLocalVariable(environment, variableName, variableValue);

        Assertions.assertEquals(
            variableValue,
            config.getLocalVariable(environment, variableName),
            "Creating a local variable should return it when accessed"
        );
    }

    @Test
    void whenIsLocalVariableDeleted_thenAccessingItReturnsNull() {
        var config = new EnvironmentConfig();
        String environment = "test";
        String variableName = "name";
        String variableValue = "value";

        config.createEnvironment(environment);

        config.setLocalVariable(environment, variableName, variableValue);

        Assertions.assertEquals(
            variableValue,
            config.getLocalVariable(environment, variableName),
            "Created local variable should exist before deletion"
        );

        config.deleteLocalVariable(environment, variableName);

        Assertions.assertNull(
            config.getLocalVariable(environment, variableName),
            "Accessing a deleted local variable should return null"
        );
    }

    @Test
    void whenIsLocalVariableAccessedInEnvironmentThatDoesNotExist_thenExceptionIsThrown() {
        var config = new EnvironmentConfig();
        String environment = "nonexistent";
        String variableName = "name";

        Assertions.assertThrows(
            EnvironmentDoesNotExistException.class,
            () -> config.getLocalVariable(environment, variableName),
            "Accessing a local variable in environment that does not exist should throw an exception"
        );

    }

    @Test
    void whenGlobalVariableIsCreated_thenItIsAddedToConfig() {
        var config = new EnvironmentConfig();
        String variableName = "name";
        String variableValue = "value";

        config.setGlobalVariable(variableName, variableValue);

        Assertions.assertEquals(
            variableValue,
            config.getGlobalVariable(variableName),
            "Creating a global variable should return it when accessed"
        );
    }

    @Test
    void whenGlobalVariableIsDeleted_thenAccessingItReturnsNull() {
        var config = new EnvironmentConfig();
        String variableName = "name";
        String variableValue = "value";

        config.setGlobalVariable(variableName, variableValue);

        Assertions.assertEquals(
            variableValue,
            config.getGlobalVariable(variableName),
            "Global variable should exist before being deleted"
        );

        config.deleteGlobalVariable(variableName);

        Assertions.assertNull(
            config.getGlobalVariable(variableName),
            "Global variable should not exist after being deleted"
        );
    }

    @Test
    void gettingAllGlobalVariablesShouldReturnThem() {
        var config = new EnvironmentConfig();
        String variableName1 = "name1";
        String variableValue1 = "value1";

        String variableName2 = "name2";
        String variableValue2 = "value2";

        config.setGlobalVariable(variableName1, variableValue1);
        config.setGlobalVariable(variableName2, variableValue2);

        Assertions.assertEquals(
            Map.of(
                variableName1, variableValue1,
                variableName2, variableValue2
            ),
            config.getAllGlobalVariables(),
            "Getting all global variables should return them"
        );
    }

    @Test
    void gettingAllLocalVariablesInEnvironmentShouldReturnThem() {
        var config = new EnvironmentConfig();
        String environment = "env";
        String variableName1 = "name1";
        String variableValue1 = "value1";

        String variableName2 = "name2";
        String variableValue2 = "value2";

        config.createEnvironment(environment);

        config.setLocalVariable(environment, variableName1, variableValue1);
        config.setLocalVariable(environment, variableName2, variableValue2);

        Assertions.assertEquals(
            Map.of(
                variableName1, variableValue1,
                variableName2, variableValue2
            ),
            config.getAllLocalVariables(environment),
            "Getting all local variables in environment should return them"
        );
    }

    @Test
    void settingScriptWorks() {
        var config = new EnvironmentConfig();
        String environment = "env";
        String script = "script";

        config.createEnvironment(environment);
        config.setScript(environment, script);

        Assertions.assertEquals(
            config.getScript(environment),
            script,
            "Script should be returned when set"
        );
    }

    @Test
    void whenGettingScriptThatDoesNotExist_thenExcept() {
        var config = new EnvironmentConfig();
        String environment = "env";

        Assertions.assertNull(
            config.getScript(environment),
            "Accessing a script that does not exist should return null"
        );
    }

}