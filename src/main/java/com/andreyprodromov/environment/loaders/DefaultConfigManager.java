package com.andreyprodromov.environment.loaders;

import com.google.gson.Gson;
import com.andreyprodromov.environment.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultConfigManager extends ConfigManager {

    private static final Path FOLDER_PATH = Path.of(System.getenv("APPDATA"), "scripter");
    private static final Path FILEPATH = FOLDER_PATH.resolve("config");
    private static final Gson GSON = new Gson();

    private static Config config;

    @Override
    public Config getConfig() {
        if (config == null)
            config = loadConfig();

        if (config == null)
            config = new Config();

        return config;
    }

    private Config loadConfig() {
        try {
            if (!Files.exists(FILEPATH)) {
                Files.createDirectories(FOLDER_PATH);
                Files.createFile(FILEPATH);
            }

            String input = Files.readString(FILEPATH);
            return GSON.fromJson(input, Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveConfig() {
        try {
            Files.writeString(FILEPATH, GSON.toJson(config));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
