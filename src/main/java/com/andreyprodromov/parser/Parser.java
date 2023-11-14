package com.andreyprodromov.parser;

public interface Parser {
    String parse(String environment, String[] args);

    static Parser get() {
        return new DefaultParser();
    }
}
