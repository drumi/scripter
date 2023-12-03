package com.andreyprodromov.runtime;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Data class representing an execution environment.
 */
public class Environment {

    private final String name;
    private final Map<String, String> localVariables = new HashMap<>();

    private String script;

    /**
     * @param name the name of the environment
     */
    public Environment(String name) {
        this.name = name;
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

}
