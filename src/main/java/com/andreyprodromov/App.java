package com.andreyprodromov;

import com.andreyprodromov.handlers.DefaultHandler;

public class App {

    public static void main(String[] args) {
        var handler = new DefaultHandler();
        try {
            handler.handle(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
