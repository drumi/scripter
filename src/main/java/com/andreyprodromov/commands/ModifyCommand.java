package com.andreyprodromov.commands;

import com.andreyprodromov.commands.exceptions.CommandDoesNotExistException;
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ModifyCommand implements Command {

    private static final int MODIFICATION_TYPE_INDEX = 1;
    private final String[] args;
    private final RuntimeConfigManager runtimeConfigManager;

    public ModifyCommand(String[] args, RuntimeConfigManager runtimeConfigManager) {
        this.args = args;
        this.runtimeConfigManager = runtimeConfigManager;
    }

    @Override
    public void execute() {
        String command = args[MODIFICATION_TYPE_INDEX];
        var config = runtimeConfigManager.getConfig();

        switch (command) {
            case "-slv", "--set-local-variable" -> {
                final int environmentIndex = MODIFICATION_TYPE_INDEX + 1;
                final int variableNameIndex = MODIFICATION_TYPE_INDEX + 2;
                final int variableValueIndex = MODIFICATION_TYPE_INDEX + 3;

                config.setLocalVariable(
                    args[environmentIndex],
                    args[variableNameIndex],
                    args[variableValueIndex]
                );
            }
            case "-sgv", "--set-global-variable" -> {
                final int variableNameIndex = MODIFICATION_TYPE_INDEX + 1;
                final int variableValueIndex = MODIFICATION_TYPE_INDEX + 2;

                config.setGlobalVariable(
                    args[variableNameIndex],
                    args[variableValueIndex]
                );
            }
            case "-ss", "--set-script" -> {
                final int environmentIndex = MODIFICATION_TYPE_INDEX + 1;
                final int scriptFileIndex = MODIFICATION_TYPE_INDEX + 2;

                try {
                    String script = Files.readString(Path.of(args[scriptFileIndex]));
                    config.setScript(
                        args[environmentIndex],
                        script
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> {
                throw new CommandDoesNotExistException(
                    String.format("\"%s\" is not an existing option for --modify command", command)
                );
            }
        }

        runtimeConfigManager.saveConfig();
    }

}
