package com.andreyprodromov.scripter;

import com.andreyprodromov.scripter.commands.factories.CommandFactory;
import com.andreyprodromov.scripter.commands.factories.DefaultCommandFactory;
import com.andreyprodromov.scripter.handlers.DefaultHandler;
import com.andreyprodromov.scripter.handlers.Handler;
import com.andreyprodromov.scripter.parsers.DefaultParser;
import com.andreyprodromov.scripter.parsers.Parser;
import com.andreyprodromov.scripter.platform.DefaultExecutor;
import com.andreyprodromov.scripter.platform.Executor;
import com.andreyprodromov.scripter.runtime.loaders.DefaultEnvironmentConfigLoader;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.regex.Pattern;

public final class App {

    private static final Pattern variablePattern = Pattern.compile("%\\[(\\S+)]%");

    private static final Path DEFAULT_FOLDER = Path.of(
        System.getProperty("user.home"),
        ".scripter"
    );

    private static final String CONFIG_FILE_NAME = "config";

    private static final int EXIT_FAILURE = -1;

    public static void main(String[] args) {
        int exitStatus;

        // Wire up dependencies
        try (var environmentConfigLoader = new DefaultEnvironmentConfigLoader(DEFAULT_FOLDER, CONFIG_FILE_NAME)) {

            Parser parser = new DefaultParser(variablePattern, environmentConfigLoader);
            OutputStream outputStream = System.out;
            Executor executor = new DefaultExecutor();

            CommandFactory commandFactory = new DefaultCommandFactory(environmentConfigLoader, parser, outputStream, executor);
            Handler handler = new DefaultHandler(commandFactory);

            // Handle input
            try {
                exitStatus = handler.handle(args);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                exitStatus = EXIT_FAILURE;
            }
        }

        System.exit(exitStatus);
    }
}
