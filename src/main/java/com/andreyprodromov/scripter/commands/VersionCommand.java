package com.andreyprodromov.scripter.commands;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Properties;

/**
 * Command for outputting the program's version.
 */
public final class VersionCommand implements Command {

    private static final int EXECUTION_SUCCESS = 0;

    private final PrintStream outputStream;

    private final String version;

    /**
     * @param outputStream
     *        the output stream where the version will be written
     */
    public VersionCommand(OutputStream outputStream) {
        Objects.requireNonNull(outputStream, "outputStream must not be null");
        this.outputStream = new PrintStream(outputStream);

        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream("/project.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        version = properties.getProperty("version");
    }

    @Override
    public int execute() {
        outputStream.printf("Version: %s%n", version);

        return EXECUTION_SUCCESS;
    }
}
