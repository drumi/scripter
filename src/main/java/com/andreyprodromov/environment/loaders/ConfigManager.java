package com.andreyprodromov.environment.loaders;

import com.andreyprodromov.environment.Config;

public abstract class ConfigManager {

    private static ConfigManager manager = new DefaultConfigManager();

    public abstract Config getConfig();
    public abstract void saveConfig();

    public static ConfigManager get() {
        return manager;
    };
}
