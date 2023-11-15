package com.andreyprodromov.runtime.loaders;

import com.andreyprodromov.runtime.RuntimeConfig;

public interface RuntimeConfigManager {

    RuntimeConfig getConfig();
    void saveConfig();

}
