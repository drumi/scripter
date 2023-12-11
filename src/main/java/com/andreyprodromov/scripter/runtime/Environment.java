package com.andreyprodromov.scripter.runtime;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Data class representing an execution environment.
 */
final class Environment {

    private final String name;
    private final Map<String, String> localVariables = new HashMap<>();

    private String script;

    /**
     * @param name
     *        the name of the environment
     */
    public Environment(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAllLocalVariables() {
        return Collections.unmodifiableMap(localVariables);
    }

    public void setLocalVariable(String name, String value) {
        localVariables.put(name, value);
    }

    @Nullable
    public String getLocalVariable(String name) {
        return localVariables.get(name);
    }

    public void deleteLocalVariable(String name) {
        localVariables.remove(name);
    }

    @Nullable
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void deleteScript() {
        this.script = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Environment that = (Environment) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
