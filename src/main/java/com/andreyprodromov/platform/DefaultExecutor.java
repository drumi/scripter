package com.andreyprodromov.platform;

import java.io.IOException;

/**
 * The default platform executor.
 */
public class DefaultExecutor implements Executor {

    @Override
    public void execute(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
