package com.andreyprodromov.scripter.runtime.loaders;

import com.andreyprodromov.scripter.runtime.EnvironmentConfig;

public interface EnvironmentConfigLoader {

    /**
     * @return the {@link EnvironmentConfig}. Changes made to the {@code EnvironmentConfig} are reflected internally
     */
    EnvironmentConfig getConfig();

    /**
     * persists the {@link EnvironmentConfig} into durable storage
     */
    void saveConfig();

}
