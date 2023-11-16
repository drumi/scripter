package com.andreyprodromov.runtime.loaders;

import com.google.gson.Gson;
import com.andreyprodromov.runtime.RuntimeConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultRuntimeConfigManager implements RuntimeConfigManager {

    private static final Path FOLDER_PATH = getDefaultFolderPath();
    private static final Path FILE_PATH = FOLDER_PATH.resolve("config");
    private static final Gson GSON = new Gson();

    private RuntimeConfig runtimeConfig;

    @Override
    public RuntimeConfig getConfig() {
        if (runtimeConfig == null)
            runtimeConfig = loadConfig();

        if (runtimeConfig == null)
            runtimeConfig = new RuntimeConfig();

        return runtimeConfig;
    }

    private RuntimeConfig loadConfig() {
        try {
            if (!Files.exists(FILE_PATH)) {
                Files.createDirectories(FOLDER_PATH);
                Files.createFile(FILE_PATH);
            }

            String input = Files.readString(FILE_PATH);
            return GSON.fromJson(input, RuntimeConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveConfig() {
        try {
            Files.writeString(FILE_PATH, GSON.toJson(runtimeConfig));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path getDefaultFolderPath() {
        return Path.of(
            System.getProperty("user.home"),
            ".scripter"
        );
    }
}
