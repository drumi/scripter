package com.andreyprodromov.environment;

import com.andreyprodromov.environment.exceptions.EnvironmentDoesNotExistException;
import com.andreyprodromov.environment.exceptions.ScriptDoesNotExistException;
import com.andreyprodromov.environment.exceptions.VariableIsNotSetException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Config {

    private final Set<String> environments = new HashSet<>();
    private final Map<String, String> globalVariables = new HashMap<>();
    private final Map<String, Map<String, String>> localVariables = new HashMap<>();
    private final Map<String, String> scripts = new HashMap<>();

    public void createEnvironment(String environmentName) {
        environments.add(environmentName);
        localVariables.computeIfAbsent(environmentName, k -> new HashMap<>());
    }

    public void deleteEnvironment(String environtmentName) {
        environments.remove(environtmentName);
        localVariables.remove(environtmentName);
        scripts.remove(environtmentName);
    }

    public void deleteLocalVariable(String environtmentName, String variableName) {
        var environmentVariables = localVariables.get(environtmentName);

        if (environmentVariables != null)
            environmentVariables.remove(variableName);
    }

    public void deleteGlobalVariable(String variableName) {
        globalVariables.remove(variableName);
    }

    public Set<String> getEnvironments() {
        return environments;
    }

    public void setGlobalVariable(String name, String value) {
        globalVariables.put(name, value);
    }

    public void setLocalVariable(String environmentName, String variableName, String value) {
        if (!environments.contains(environmentName))
            throw createEnvirontmentDoesNotExistException(environmentName);

        localVariables.get(environmentName)
                      .put(variableName, value);
    }

    public void setScript(String environmentName, String script) {
        if (!environments.contains(environmentName))
            throw createEnvirontmentDoesNotExistException(environmentName);

        scripts.put(environmentName, script);
    }

    public String getVariable(String environmentName, String variableName) {
        Map<String, String> localVariablesForEnvironment = localVariables.get(environmentName);

        if (localVariablesForEnvironment != null) {
            String localVariable = localVariablesForEnvironment.get(variableName);

            if (localVariable != null) {
                return localVariable;
            }
        }

        String returnedVariable = globalVariables.get(variableName);

        if (returnedVariable != null) {
            return returnedVariable;
        }

        throw createVariableIsNotSetException(variableName);
    }

    public Map<String, String> getAllGlobalVariables() {
        return globalVariables;
    }

    public Map<String, String> getAllLocalVariables(String environmentName) {
        return localVariables.getOrDefault(environmentName, new HashMap<>());
    }

    public String getScript(String environmentName) {
        if (scripts.get(environmentName) == null)
            throw new ScriptDoesNotExistException(
                String.format("\"%s\" does not have a script set", environmentName)
            );

        return scripts.get(environmentName);
    }

    private EnvironmentDoesNotExistException createEnvirontmentDoesNotExistException(String environmentName) {
        return new EnvironmentDoesNotExistException(
            String.format("\"%s\" does not exist as an com.andreyprodromov.environment", environmentName)
        );
    }

    private VariableIsNotSetException createVariableIsNotSetException(String variableName) {
        return new VariableIsNotSetException(
            String.format("\"%s\" variable is not set", variableName)
        );
    }
}
