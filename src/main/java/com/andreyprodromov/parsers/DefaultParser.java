package com.andreyprodromov.parsers;

import com.andreyprodromov.parsers.exceptions.ScriptDoesNotExistException;
import com.andreyprodromov.parsers.exceptions.VariableIsNotSetException;
import com.andreyprodromov.runtime.RuntimeConfig;
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;

public class DefaultParser implements Parser {

    private final String variablePrefix;
    private final String variableSuffix;
    private final RuntimeConfigManager runtimeConfigManager;

    public DefaultParser(String variablePrefix, String variableSuffix,
                         RuntimeConfigManager runtimeConfigManager) {
        this.variablePrefix = variablePrefix;
        this.variableSuffix = variableSuffix;
        this.runtimeConfigManager = runtimeConfigManager;
    }

    @Override
    public String parse(String environment, String[] args) {
        RuntimeConfig runtimeConfig = runtimeConfigManager.getConfig();

        String script = runtimeConfig.getScript(environment);

        if (script == null)
            throw new ScriptDoesNotExistException(
                String.format("\"%s\" does not have a script set", environment)
            );

        script = putCommandlineArguments(script, args);

        script = putEnvironmentVariables(script, environment, runtimeConfig);

        return script;
    }

    private String putCommandlineArguments(String script, String[] args) {
        for (int i = 0; i < args.length; i++) {
            String commandLineArgumentTemplate = String.format(
                "%s%d%s",
                variablePrefix,
                i + 1, // Command-line arguments start from 1
                variableSuffix
            );

            script = script.replace(commandLineArgumentTemplate, args[i]);
        }

        return script;
    }


    private String putEnvironmentVariables(String script, String environment, RuntimeConfig config) {
        int startIndex = script.indexOf(variablePrefix);
        int endIndex = script.indexOf(variableSuffix);
        while (startIndex != -1) {
            String variable = script.substring(startIndex + variablePrefix.length() , endIndex);

            String valueOfVariable = getVariable(environment, variable, config);

            script = script.replace(
                variablePrefix + variable + variableSuffix,
                valueOfVariable
            );

            startIndex = script.indexOf(variablePrefix);
            endIndex = script.indexOf(variableSuffix);
        }

        return script;
    }

    private String getVariable(String environment, String variableName, RuntimeConfig config) {
        String localVariable = config.getLocalVariable(environment, variableName);

        if (localVariable != null)
            return localVariable;

        String globalVariable = config.getGlobalVariable(variableName);

        if (globalVariable != null)
            return globalVariable;

        throw new VariableIsNotSetException(
            String.format("\"%s\" is not set", variableName)
        );
    }
}
