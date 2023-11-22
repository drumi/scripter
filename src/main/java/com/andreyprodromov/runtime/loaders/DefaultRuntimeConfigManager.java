package com.andreyprodromov.runtime.loaders;

import com.google.gson.Gson;
import com.andreyprodromov.runtime.RuntimeConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultRuntimeConfigManager implements RuntimeConfigManager {

    private static final Gson GSON = new Gson();

    private final Path folderPath;
    private final Path filePath;

    private RuntimeConfig runtimeConfig;

    public DefaultRuntimeConfigManager(Path folderPath) {
        this.folderPath = folderPath;
        this.filePath = folderPath.resolve("config");
    }

    @Override
    public RuntimeConfig getConfig() {
        if (runtimeConfig == null)
            runtimeConfig = loadConfig();

        return runtimeConfig;
    }

    private RuntimeConfig loadConfig() {
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(folderPath);
                Files.createFile(filePath);
            }

            String input = Files.readString(filePath);
            return GSON.fromJson(input, RuntimeConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveConfig() {
        try {
            Files.writeString(filePath, GSON.toJson(runtimeConfig));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
