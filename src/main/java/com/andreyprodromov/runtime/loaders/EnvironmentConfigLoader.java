package com.andreyprodromov.runtime.loaders;

import com.andreyprodromov.runtime.EnvironmentConfig;

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
