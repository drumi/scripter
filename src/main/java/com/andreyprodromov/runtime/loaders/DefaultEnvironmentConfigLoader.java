package com.andreyprodromov.runtime.loaders;

import com.andreyprodromov.runtime.EnvironmentConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultEnvironmentConfigLoader implements EnvironmentConfigLoader {

    private static final Gson GSON = new Gson();

    private final Path folderPath;
    private final Path filePath;
    private final EnvironmentConfig environmentConfig;

    public DefaultEnvironmentConfigLoader(Path folderPath) {
        this.folderPath = folderPath;
        this.filePath = folderPath.resolve("config");

        if (!Files.exists(filePath)) {
            createDirectories();
        }

        this.environmentConfig = loadConfig();
    }

    @Override
    public EnvironmentConfig getConfig() {
        return environmentConfig;
    }

    private EnvironmentConfig loadConfig() {
        try {
            String input = Files.readString(filePath);
            var config = GSON.fromJson(input, EnvironmentConfig.class);
            return config == null ? new EnvironmentConfig() : config;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDirectories() {
        try {
            Files.createDirectories(folderPath);
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveConfig() {
        try {
            Files.writeString(filePath, GSON.toJson(environmentConfig));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
