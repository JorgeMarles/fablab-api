package com.example.demo.utils;

public class StringGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int DEFAULT_LENGTH = 5;

    public static String generateRandomString() {
        return generateRandomString(DEFAULT_LENGTH);
    }

    public static String generateRandomString(int length) {
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * ALPHABET.length());
            result.append(ALPHABET.charAt(index));
        }
        return result.toString();
    }
}
