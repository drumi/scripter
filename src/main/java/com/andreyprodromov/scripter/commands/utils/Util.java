package com.andreyprodromov.scripter.commands.utils;

import com.andreyprodromov.scripter.commands.exceptions.ArgumentsMismatchException;

import java.util.Objects;

/**
 * Utility class for common validation.
 */
public final class Util {

    private Util() { }

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
    @SafeVarargs
    public static <T> void assertExactLength(int length, T... args) {
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
    @SafeVarargs
    public static <T> void assertMinimumLength(int length, T... args) {
        if (args.length < length)
            throw new ArgumentsMismatchException("Mismatched arguments count");
    }

    /**
     * @param array
     *        the array on which checks are performed
     *
     * @throws NullPointerException if the array is null or if any element of the array is null
     */
    public static <T> void assertAllNonNull(T[] array) {
        Objects.requireNonNull(array, "array must not be null");

        for (T el : array)
            Objects.requireNonNull(el, "array must not contain null elements");
    }
}
