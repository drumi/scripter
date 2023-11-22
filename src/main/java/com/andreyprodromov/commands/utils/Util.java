package com.andreyprodromov.commands.utils;

import com.andreyprodromov.commands.exceptions.ArgumentsMismatchException;

public final class Util {

    private Util() { }

    public static void assertExactLength(int length, String... args) {
        if (args.length != length)
            throw new ArgumentsMismatchException("Mismatched arguments count");
    }

    public static void assertMinimumLength(int length, String... args) {
        if (args.length < length)
            throw new ArgumentsMismatchException("Mismatched arguments count");
    }
}
