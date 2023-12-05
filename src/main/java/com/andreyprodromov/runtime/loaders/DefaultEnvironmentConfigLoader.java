package com.andreyprodromov.runtime.loaders;

import com.andreyprodromov.runtime.EnvironmentConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * The default config loader.
 * Provides save/load functionality for the user config.
 * The config file is exclusively locked till {@link #close()} method is called.
 * This class is not thread-safe.
 */
public class DefaultEnvironmentConfigLoader implements EnvironmentConfigLoader, AutoCloseable {

    private static final Gson GSON = new Gson();

    private final Path folderPath;
    private final Path filePath;
    private final Path backupFilePath;

    private final EnvironmentConfig environmentConfig;

    private final RandomAccessFile configFile;

    public DefaultEnvironmentConfigLoader(Path folderPath, String fileName) {
        this.folderPath = folderPath;
        this.filePath = folderPath.resolve(fileName);
        this.backupFilePath = folderPath.resolve(fileName + ".bak");

        createDirectories();

        this.configFile = openAndLockFile();

        createBackup();

        this.environmentConfig = loadConfig();
    }

    @Override
    public EnvironmentConfig getConfig() {
        return environmentConfig;
    }

    private EnvironmentConfig loadConfig() {
        try {
            int length = (int) configFile.length();
            byte[] bytes = new byte[length];

            configFile.seek(0);
            configFile.readFully(bytes);

            String input = new String(bytes);

            EnvironmentConfig config = GSON.fromJson(input, EnvironmentConfig.class);

            return config == null ? new EnvironmentConfig() : config;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveConfig() {
        try {
            byte[] jsonBytes = GSON.toJson(environmentConfig).getBytes();

            configFile.seek(0);
            configFile.write(jsonBytes);

            configFile.setLength(jsonBytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDirectories() {
        try {
            Files.createDirectories(folderPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createBackup() {
        try {
            if (Files.exists(filePath)) {
                configFile.seek(0);
                var inputStream = Channels.newInputStream(
                    configFile.getChannel()
                );

                Files.copy(inputStream, backupFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RandomAccessFile openAndLockFile() {
        RandomAccessFile file;

        try {
            file = new RandomAccessFile(filePath.toFile(), "rw");
            file.getChannel().lock();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    @Override
    public void close() throws Exception {
        configFile.close();
    }
}
