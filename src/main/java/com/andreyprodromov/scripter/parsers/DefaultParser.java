package com.andreyprodromov.scripter.parsers;

import com.andreyprodromov.scripter.commands.utils.Util;
import com.andreyprodromov.scripter.parsers.exceptions.ScriptDoesNotExistException;
import com.andreyprodromov.scripter.parsers.exceptions.VariableIsNotSetException;
import com.andreyprodromov.scripter.runtime.EnvironmentConfig;
import com.andreyprodromov.scripter.runtime.loaders.EnvironmentConfigLoader;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The default parser for scripts.
 */
public final class DefaultParser implements Parser {

    private static final String POSITIVE_INTEGER_REGEX = "\\d+";
    private static final int CAPTURED_GROUP_INDEX = 1;

    private final Pattern variablePattern;
    private final EnvironmentConfigLoader environmentConfigLoader;

    /**
     * @param variablePattern
     *        the pattern to be matched against.
     *        Must contain exactly one capture group,
     *        which is used to get the variable name
     *
     * @param environmentConfigLoader
     *        the environment config loader
     */
    public DefaultParser(Pattern variablePattern, EnvironmentConfigLoader environmentConfigLoader) {
        this.variablePattern = Objects.requireNonNull(variablePattern, "variablePattern must not be null");
        this.environmentConfigLoader =
            Objects.requireNonNull(environmentConfigLoader, "environmentConfigLoader must not be null");
    }

    @Override
    public String parse(String environment, String[] args) {
        Objects.requireNonNull(environment, "environment must not be null");
        Util.assertAllNonNull(args);

        EnvironmentConfig environmentConfig = environmentConfigLoader.getConfig();
        String script =
            environmentConfig.getScript(environment)
                             .orElseThrow(() -> newScriptDoesNotExistException(environment));

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

                // Subtracting 1, because command-line arguments start from 1, but arrays start from 0
                String argument = args[value - 1];
                matcher.appendReplacement(sb, argument);
            } else {
                String value = getVariable(environment, variableName, environmentConfig);
                matcher.appendReplacement(sb, value);
            }
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    private ScriptDoesNotExistException newScriptDoesNotExistException(String environment) {
        return new ScriptDoesNotExistException(
            "\"%s\" does not have a script set".formatted(environment)
        );
    }

    private String getVariable(String environment, String variableName, EnvironmentConfig config) {
        return config.getLocalVariable(environment, variableName)
                     .or(() -> config.getGlobalVariable(variableName))
                     .orElseThrow(
                         () -> new VariableIsNotSetException("\"%s\" is not set".formatted(variableName))
                     );
    }
}
