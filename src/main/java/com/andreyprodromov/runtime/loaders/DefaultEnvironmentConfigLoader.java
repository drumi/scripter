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

    private EnvironmentConfig environmentConfig;

    public DefaultEnvironmentConfigLoader(Path folderPath) {
        this.folderPath = folderPath;
        this.filePath = folderPath.resolve("config");
    }

    @Override
    public EnvironmentConfig getConfig() {
        if (environmentConfig == null)
            environmentConfig = loadConfig();

        return environmentConfig;
    }

    private EnvironmentConfig loadConfig() {
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(folderPath);
                Files.createFile(filePath);
            }

            String input = Files.readString(filePath);
            return GSON.fromJson(input, EnvironmentConfig.class);
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
