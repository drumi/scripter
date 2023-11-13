package com.andreyprodromov;

import com.andreyprodromov.handlers.DefaultHandler;

public class App {

    public static void main(String[] args) {
        var handler = new DefaultHandler();
        handler.handle(args);
    }

}
