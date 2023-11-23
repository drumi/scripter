package com.andreyprodromov.runtime.loaders;

import com.andreyprodromov.runtime.EnvironmentConfig;

public interface EnvironmentConfigLoader {

    EnvironmentConfig getConfig();
    void saveConfig();

}
