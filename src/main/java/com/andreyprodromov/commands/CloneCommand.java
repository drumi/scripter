package com.andreyprodromov.commands;

import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;

public class CloneCommand implements Command {

    private static final int ORIGINAL_ENVIRONMENT_NAME_INDEX = 1;
    private static final int CLONED_ENVIRONMENT_NAME_INDEX = 2;

    private final String[] args;
    private final RuntimeConfigManager runtimeConfigManager;

    public CloneCommand(String[] args, RuntimeConfigManager runtimeConfigManager) {
        this.args = args;
        this.runtimeConfigManager = runtimeConfigManager;
    }

    @Override
    public void execute() {
        String originalEnvironment = args[ORIGINAL_ENVIRONMENT_NAME_INDEX];
        String clonedEnvironment = args[CLONED_ENVIRONMENT_NAME_INDEX];

        var config = runtimeConfigManager.getConfig();

        config.createEnvironment(clonedEnvironment);

        config.getAllLocalVariables(originalEnvironment)
              .forEach((key, value) -> config.setLocalVariable(clonedEnvironment, key, value));

        String script = config.getScript(originalEnvironment);
        if (script != null)
            config.setScript(clonedEnvironment,script);
    }

}
