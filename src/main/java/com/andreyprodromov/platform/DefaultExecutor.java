package com.andreyprodromov.platform;

import java.io.IOException;

public class DefaultExecutor implements Executor {

    @Override
    public void execute(String argument) {
        try {
            Runtime.getRuntime().exec(argument);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
