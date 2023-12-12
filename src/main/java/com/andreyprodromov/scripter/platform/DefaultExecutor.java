package com.andreyprodromov.scripter.platform;

import java.io.IOException;
import java.util.Objects;

/**
 * The default platform executor.
 */
public final class DefaultExecutor implements Executor {

    private static final String DELIMITER_REGEX = "\\s+";

    @Override
    public int execute(String cmd) {
        Objects.requireNonNull(cmd, "cmd must not be null");

        try {
            ProcessBuilder builder = new ProcessBuilder(cmd.split(DELIMITER_REGEX));
            Process process = builder.start();

            process.waitFor();

            return process.exitValue();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
