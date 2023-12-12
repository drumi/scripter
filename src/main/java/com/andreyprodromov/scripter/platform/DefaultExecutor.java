package com.andreyprodromov.scripter.platform;

import java.io.IOException;
import java.util.Objects;

/**
 * The default platform executor.
 */
public final class DefaultExecutor implements Executor {

    @Override
    public int execute(String cmd) {
        Objects.requireNonNull(cmd, "cmd must not be null");

        int status;

        try {
            ProcessBuilder builder = new ProcessBuilder(cmd);
            Process process = builder.start();

            process.waitFor();

            status = process.exitValue();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return status;
    }

}
