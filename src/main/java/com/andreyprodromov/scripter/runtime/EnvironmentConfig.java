package com.andreyprodromov.scripter.runtime;

import com.andreyprodromov.scripter.runtime.exceptions.EnvironmentAlreadyExistsException;
import com.andreyprodromov.scripter.runtime.exceptions.EnvironmentDoesNotExistException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * The environment configuration. This includes all environments and global variables.
 */
public final class EnvironmentConfig {

    private final Map<String, Environment> environments = new HashMap<>();
    private final Map<String, String> globalVariables = new HashMap<>();

    public void createEnvironment(String environmentName) {
        Objects.requireNonNull(environmentName, "environmentName must not be null");

        if (environments.get(environmentName) != null)
            throw newEnvironmentAlreadyExistsException(environmentName);

        environments.put(environmentName, new Environment(environmentName));
    }

    public void deleteEnvironment(String environmentName) {
        Objects.requireNonNull(environmentName, "environmentName must not be null");

        getEnvironmentOrExcept(environmentName);

        environments.remove(environmentName);
    }

    public void deleteLocalVariable(String environmentName, String variableName) {
        Objects.requireNonNull(environmentName, "environmentName must not be null");
        Objects.requireNonNull(variableName, "variableName must not be null");

            environments.get(environmentName)
                        .deleteLocalVariable(variableName);
    }

    public void deleteGlobalVariable(String variableName) {
        Objects.requireNonNull(variableName, "variableName must not be null");

       globalVariables.remove(variableName);
    }

    public Set<String> getEnvironments() {
        return environments.keySet();
    }

    public void setGlobalVariable(String name, String value) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(value, "value must not be null");

        globalVariables.put(name, value);
    }

    public void setLocalVariable(String environmentName, String variableName, String value) {
        Objects.requireNonNull(environmentName, "environmentName must not be null");
        Objects.requireNonNull(variableName, "variableName must not be null");
        Objects.requireNonNull(value, "value must not be null");

        var environment = getEnvironmentOrExcept(environmentName);

        environment.setLocalVariable(variableName, value);
    }

    public Optional<String> getLocalVariable(String environmentName, String variableName) {
        Objects.requireNonNull(environmentName, "environmentName must not be null");
        Objects.requireNonNull(variableName, "variableName must not be null");

        var environment = getEnvironmentOrExcept(environmentName);

        return Optional.ofNullable(
            environment.getLocalVariable(variableName)
        );
    }

    public Optional<String> getGlobalVariable(String variableName) {
        Objects.requireNonNull(variableName, "variableName must not be null");

        return Optional.ofNullable(
            globalVariables.get(variableName)
        );
    }

    public Map<String, String> getAllGlobalVariables() {
        return Collections.unmodifiableMap(globalVariables);
    }

    public Map<String, String> getAllLocalVariables(String environmentName) {
        Objects.requireNonNull(environmentName, "environmentName must not be null");

        var environment = getEnvironmentOrExcept(environmentName);

        return environment.getAllLocalVariables();
    }

    public Optional<String> getScript(String environmentName) {
        Objects.requireNonNull(environmentName, "environmentName must not be null");

        var environment = getEnvironmentOrExcept(environmentName);

        return Optional.ofNullable(
            environment.getScript()
        );
    }

    public void setScript(String environmentName, String script) {
        Objects.requireNonNull(environmentName, "environmentName must not be null");
        Objects.requireNonNull(script, "script must not be null");

        var environment = getEnvironmentOrExcept(environmentName);

        environment.setScript(script);
    }

    public void deleteScript(String environmentName) {
        Objects.requireNonNull(environmentName, "environmentName must not be null");

        var environment = getEnvironmentOrExcept(environmentName);

        environment.deleteScript();
    }

    private EnvironmentDoesNotExistException newEnvironmentDoesNotExistException(String environmentName) {
        return new EnvironmentDoesNotExistException(
            "\"%s\" does not exist as an environment".formatted(environmentName)
        );
    }

    private EnvironmentAlreadyExistsException newEnvironmentAlreadyExistsException(String environmentName) {
        return new EnvironmentAlreadyExistsException(
            "\"%s\" already exists as an environment".formatted(environmentName)
        );
    }

    private Environment getEnvironmentOrExcept(String environmentName) {
        var environment = environments.get(environmentName);

        if (environment == null)
            throw newEnvironmentDoesNotExistException(environmentName);

        return environment;
    }
}
