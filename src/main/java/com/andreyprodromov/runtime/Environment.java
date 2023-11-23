package com.andreyprodromov.runtime;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Environment {

    private final Map<String, String> localVariables = new HashMap<>();
    private final String name;

    private String script;

    public Environment(String name) {
        this.name = name;
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
