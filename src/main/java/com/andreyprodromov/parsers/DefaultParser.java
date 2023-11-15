package com.andreyprodromov.parsers;

import com.andreyprodromov.runtime.RuntimeConfig;
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;

public class DefaultParser implements Parser {

    private static final String VARIABLE_PREFIX = "%[";
    private static final String VARIABLE_SUFFIX = "]%";

    private final RuntimeConfigManager runtimeConfigManager;

    public DefaultParser(RuntimeConfigManager runtimeConfigManager) {
        this.runtimeConfigManager = runtimeConfigManager;
    }

    @Override
    public String parse(String environment, String[] args) {
        RuntimeConfig runtimeConfig = runtimeConfigManager.getConfig();
        String script = runtimeConfig.getScript(environment);


        // Put commandline arguments
        for (int i = 0; i < args.length; i++) {
            String commandLineArgumentTemplate = String.format(
                "%s%d%s",
                VARIABLE_PREFIX,
                i + 1, // Command-line arguments start from 1
                VARIABLE_SUFFIX
            );

            script = script.replace(commandLineArgumentTemplate, args[i]);
        }

        // Prepare script by putting environment variables
        int startIndex = script.indexOf(VARIABLE_PREFIX);
        int endIndex = script.indexOf(VARIABLE_SUFFIX);
        while (startIndex != -1) {
            String variable = script.substring(startIndex + VARIABLE_PREFIX.length() , endIndex);

            String valueOfVariable = runtimeConfig.getVariable(environment, variable);

            script = script.replace(
                VARIABLE_PREFIX + variable + VARIABLE_SUFFIX,
                valueOfVariable
            );

            startIndex = script.indexOf(VARIABLE_PREFIX);
            endIndex = script.indexOf(VARIABLE_SUFFIX);
        }

        return script;
    }
}
