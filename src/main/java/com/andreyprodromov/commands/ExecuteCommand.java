package com.andreyprodromov.commands;

import com.andreyprodromov.environment.Config;
import com.andreyprodromov.environment.loaders.ConfigManager;

import java.io.IOException;

public final class ExecuteCommand implements Command {

    private static final int ENVIRONMENT_NAME_INDEX = 1;
    private static final String VARIABLE_PREFIX = "%[";
    private static final String VARIABLE_SUFFIX = "]%";

    private final String[] args;

    public ExecuteCommand(String[] args) {
        this.args = args;
    }

    @Override
    public void execute() {
        String environmentName = args[ENVIRONMENT_NAME_INDEX];
        Config config = ConfigManager.get().getConfig();

        String script = config.getScript(environmentName);


        // Put commandline arguments
        for (int i = ENVIRONMENT_NAME_INDEX + 1; i < args.length; i++) {
            String commandLineArgumentTemplate = String.format(
                "%s%d%s",
                VARIABLE_PREFIX,
                i - ENVIRONMENT_NAME_INDEX,
                VARIABLE_SUFFIX
            );

            script = script.replace(commandLineArgumentTemplate, args[i]);
        }

        // Prepare script by putting environment variables
        int startIndex = script.indexOf(VARIABLE_PREFIX);
        int endIndex = script.indexOf(VARIABLE_SUFFIX);
        while (startIndex != -1) {
            String variable = script.substring(startIndex + VARIABLE_PREFIX.length() , endIndex);

            String valueOfVariable = config.getVariable(environmentName, variable);

            script = script.replace(
                VARIABLE_PREFIX + variable + VARIABLE_SUFFIX,
                valueOfVariable
            );

            startIndex = script.indexOf(VARIABLE_PREFIX);
            endIndex = script.indexOf(VARIABLE_SUFFIX);
        }

        // Execute script
        try {
            System.out.println(script);
            Runtime.getRuntime().exec(script);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
