package com.andreyprodromov.commands.utils;

import com.andreyprodromov.commands.exceptions.ArgumentsMismatchException;

/**
 * Utility class for common validation.
 */
public final class Util {

    private Util() {
        // Intentionally empty
    }

    /**
     * @param length
     *        the required length to check against
     *
     * @param args
     *        the arguments whose length is checked
     *
     * @throws ArgumentsMismatchException
     *         when the {@code length} is not equal to {@code args.length}
     */
    public static void assertExactLength(int length, String... args) {
        if (args.length != length)
            throw new ArgumentsMismatchException("Mismatched arguments count");
    }

    /**
     * @param length
     *        the required length to check against
     *
     * @param args
     *        the arguments whose length is checked
     *
     * @throws ArgumentsMismatchException
     *         when the {@code length} is less than {@code args.length}
     */
    public static void assertMinimumLength(int length, String... args) {
        if (args.length < length)
            throw new ArgumentsMismatchException("Mismatched arguments count");
    }
}
