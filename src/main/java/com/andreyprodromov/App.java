package com.andreyprodromov;

import com.andreyprodromov.commands.factories.CommandFactory;
import com.andreyprodromov.commands.factories.DefaultCommandFactory;
import com.andreyprodromov.handlers.DefaultHandler;
import com.andreyprodromov.handlers.Handler;
import com.andreyprodromov.parsers.DefaultParser;
import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.platform.DefaultExecutor;
import com.andreyprodromov.platform.Executor;
import com.andreyprodromov.runtime.loaders.DefaultEnvironmentConfigLoader;
import com.andreyprodromov.runtime.loaders.EnvironmentConfigLoader;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class App {

    private static final Pattern variablePattern = Pattern.compile("%\\[(\\S+)]%");

    private static final Path DEFAULT_FOLDER = Path.of(
        System.getProperty("user.home"),
        ".scripter"
    );

    private static final String CONFIG_FILE_NAME = "config";

    private static final int EXIT_FAILURE = -1;

    public static void main(String[] args) {

        // Wire up dependencies
        EnvironmentConfigLoader environmentConfigLoader =
            new DefaultEnvironmentConfigLoader(DEFAULT_FOLDER, CONFIG_FILE_NAME);
        Parser parser = new DefaultParser(variablePattern, environmentConfigLoader);
        OutputStream outputStream = System.out;
        Executor executor = new DefaultExecutor();

        CommandFactory commandFactory = new DefaultCommandFactory(environmentConfigLoader, parser, outputStream, executor);
        Handler handler = new DefaultHandler(commandFactory);

        // Handle input
        try {
            handler.handle(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(EXIT_FAILURE);
        }
    }
}
