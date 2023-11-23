package com.andreyprodromov.runtime;

import com.andreyprodromov.runtime.exceptions.EnvironmentAlreadyExistsException;
import com.andreyprodromov.runtime.exceptions.EnvironmentDoesNotExistException;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EnvironmentConfig {

    private final Map<String, Environment> environments = new HashMap<>();

    public void createEnvironment(String environmentName) {
        if (environments.get(environmentName) != null)
            throw createEnvironmentAlreadyExistsException(environmentName);

        environments.put(environmentName, new Environment(environmentName));
    }

    public void deleteEnvironment(String environmentName) {
        getEnvironmentOrExcept(environmentName);

        environments.remove(environmentName);
    }

    public void deleteLocalVariable(String environmentName, String variableName) {
            environments.get(environmentName)
                        .deleteLocalVariable(variableName);
    }

    public void deleteGlobalVariable(String name) {
        Environment.deleteGlobalVariable(name);
    }

    public Set<String> getEnvironments() {
        return environments.keySet();
    }

    public void setGlobalVariable(String name, String value) {
        Environment.setGlobalVariable(name, value);
    }

    public void setLocalVariable(String environmentName, String variableName, String value) {
        var environment = getEnvironmentOrExcept(environmentName);

        environment.setLocalVariable(variableName, value);
    }

    @Nullable
    public String getLocalVariable(String environmentName, String variableName) {
        var environment = getEnvironmentOrExcept(environmentName);

        return environment.getLocalVariable(variableName);
    }

    @Nullable
    public String getGlobalVariable(String variableName) {
        return Environment.getGlobalVariable(variableName);
    }

    public Map<String, String> getAllGlobalVariables() {
        return Environment.getAllGlobalVariables();
    }

    public Map<String, String> getAllLocalVariables(String environmentName) {
        var environment = getEnvironmentOrExcept(environmentName);

        return environment.getAllLocalVariables();
    }

    @Nullable
    public String getScript(String environmentName) {
        var environment = getEnvironmentOrExcept(environmentName);

        return environment.getScript();
    }

    public void setScript(String environmentName, String script) {
        var environment = getEnvironmentOrExcept(environmentName);

        environment.setScript(script);
    }

    public void deleteScript(String environmentName) {
        var environment = getEnvironmentOrExcept(environmentName);

        environment.deleteScript();
    }

    private EnvironmentDoesNotExistException createEnvironmentDoesNotExistException(String environmentName) {
        return new EnvironmentDoesNotExistException(
            "\"%s\" does not exist as an environment".formatted(environmentName)
        );
    }

    private EnvironmentAlreadyExistsException createEnvironmentAlreadyExistsException(String environmentName) {
        return new EnvironmentAlreadyExistsException(
            "\"%s\" already exists as an environment".formatted(environmentName)
        );
    }

    private Environment getEnvironmentOrExcept(String environmentName) {
        var environment = environments.get(environmentName);

        if (environment == null)
            throw createEnvironmentDoesNotExistException(environmentName);

        return environment;
    }
}
