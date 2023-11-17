package com.andreyprodromov;

import com.andreyprodromov.commands.factories.CommandFactory;
import com.andreyprodromov.commands.factories.DefaultCommandFactory;
import com.andreyprodromov.handlers.DefaultHandler;
import com.andreyprodromov.handlers.Handler;
import com.andreyprodromov.parsers.DefaultParser;
import com.andreyprodromov.parsers.Parser;
import com.andreyprodromov.platform.DefaultExecutor;
import com.andreyprodromov.platform.Executor;
import com.andreyprodromov.runtime.loaders.DefaultRuntimeConfigManager;
import com.andreyprodromov.runtime.loaders.RuntimeConfigManager;

import java.io.OutputStream;
import java.nio.file.Path;

public class App {

    private static final String PREFIX = "%[";
    private static final String SUFFIX = "]%";

    private static final Path DEFAULT_FOLDER = Path.of(
        System.getProperty("user.home"),
        ".scripter"
    );

    public static void main(String[] args) {

        // Wire up dependencies
        RuntimeConfigManager runtimeConfigManager = new DefaultRuntimeConfigManager(DEFAULT_FOLDER);
        Parser parser = new DefaultParser(PREFIX, SUFFIX, runtimeConfigManager);
        OutputStream outputStream = System.out;
        Executor executor = new DefaultExecutor();

        CommandFactory commandFactory = new DefaultCommandFactory(runtimeConfigManager, parser, outputStream, executor);
        Handler handler = new DefaultHandler(commandFactory);

        // Handle command
        try {
            handler.handle(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
