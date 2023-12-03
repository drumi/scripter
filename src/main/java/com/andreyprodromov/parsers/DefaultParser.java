package com.andreyprodromov.parsers;

import com.andreyprodromov.parsers.exceptions.ScriptDoesNotExistException;
import com.andreyprodromov.parsers.exceptions.VariableIsNotSetException;
import com.andreyprodromov.runtime.EnvironmentConfig;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The default parser for parsing scripts.
 */
public class DefaultParser implements Parser {

    private static final String POSITIVE_INTEGER_REGEX = "\\d+";
    private static final int CAPTURED_GROUP_INDEX = 1;

    private final Pattern variablePattern;
    private final EnvironmentConfigLoader environmentConfigLoader;

    /**
     * @param variablePattern the pattern to be matched against.
     *                        Must contain exactly one capture group,
     *                        which is used to get the variable name
     *
     * @param environmentConfigLoader the environment config loader
     */
    public DefaultParser(Pattern variablePattern, EnvironmentConfigLoader environmentConfigLoader) {
       this.variablePattern = variablePattern;
       this.environmentConfigLoader = environmentConfigLoader;
    }

    @Override
    public String parse(String environment, String[] args) {
        EnvironmentConfig environmentConfig = environmentConfigLoader.getConfig();
        String script = environmentConfig.getScript(environment);

        if (script == null)
            throw new ScriptDoesNotExistException(
                "\"%s\" does not have a script set".formatted(environment)
            );

        Matcher matcher = variablePattern.matcher(script);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String variableName = matcher.group(CAPTURED_GROUP_INDEX);

            if (variableName.matches(POSITIVE_INTEGER_REGEX)) {
                int value = Integer.parseInt(variableName);

                if (value > args.length)
                    throw new VariableIsNotSetException(
                        "Attempted to use command-line argument at position %d, but none was passed".formatted(value)
                    );

                matcher.appendReplacement(sb, args[value - 1]);
            } else {
                String value = getVariable(environment, variableName, environmentConfig);
                matcher.appendReplacement(sb, value);
            }
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    private String getVariable(String environment, String variableName, EnvironmentConfig config) {
        String localVariable = config.getLocalVariable(environment, variableName);

        if (localVariable != null)
            return localVariable;

        String globalVariable = config.getGlobalVariable(variableName);

        if (globalVariable != null)
            return globalVariable;

        throw new VariableIsNotSetException(
            "\"%s\" is not set".formatted(variableName)
        );
    }
}
