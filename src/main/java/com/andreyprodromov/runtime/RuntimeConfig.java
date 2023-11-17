package com.andreyprodromov.runtime;

import com.andreyprodromov.runtime.exceptions.EnvironmentDoesNotExistException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RuntimeConfig {

    private final Set<String> environments = new HashSet<>();
    private final Map<String, String> globalVariables = new HashMap<>();
    private final Map<String, Map<String, String>> localVariables = new HashMap<>();
    private final Map<String, String> scripts = new HashMap<>();

    public void createEnvironment(String environmentName) {
        environments.add(environmentName);
        localVariables.computeIfAbsent(environmentName, k -> new HashMap<>());
    }

    public void deleteEnvironment(String environmentName) {
        environments.remove(environmentName);
        localVariables.remove(environmentName);
        scripts.remove(environmentName);
    }

    public void deleteLocalVariable(String environmentName, String variableName) {
        var environmentVariables = localVariables.get(environmentName);

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
            throw createEnvironmentDoesNotExistException(environmentName);

        localVariables.get(environmentName)
                      .put(variableName, value);
    }

    public void setScript(String environmentName, String script) {
        if (!environments.contains(environmentName))
            throw createEnvironmentDoesNotExistException(environmentName);

        scripts.put(environmentName, script);
    }

    public String getLocalVariable(String environmentName, String variableName) {
        if (!environments.contains(environmentName))
            throw createEnvironmentDoesNotExistException(environmentName);

        Map<String, String> localVariablesForEnvironment = localVariables.get(environmentName);

        return localVariablesForEnvironment.get(variableName);
    }

    public String getGlobalVariable(String variableName) {
        return globalVariables.get(variableName);
    }

    public Map<String, String> getAllGlobalVariables() {
        return globalVariables;
    }

    public Map<String, String> getAllLocalVariables(String environmentName) {
        return localVariables.getOrDefault(environmentName, new HashMap<>());
    }

    public String getScript(String environmentName) {
        return scripts.get(environmentName);
    }

    private EnvironmentDoesNotExistException createEnvironmentDoesNotExistException(String environmentName) {
        return new EnvironmentDoesNotExistException(
            String.format("\"%s\" does not exist as an environment", environmentName)
        );
    }
}
