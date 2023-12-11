package com.andreyprodromov.scripter.platform;

import java.io.IOException;
import java.util.Objects;

/**
 * The default platform executor.
 */
public final class DefaultExecutor implements Executor {

    @Override
    public void execute(String cmd) {
        Objects.requireNonNull(cmd, "cmd must not be null");

        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
