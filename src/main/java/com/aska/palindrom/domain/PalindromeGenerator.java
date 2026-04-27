package com.aska.palindrom.domain;

public class PalindromeGenerator {

    public String generate(int stringLength) {
        if (stringLength <= 0) {
            throw new IllegalArgumentException("Block size must be greater than zero");
        }
        if (stringLength < 10) {
            throw new IllegalArgumentException(
                    "Generated palindrome length must be at least 10 characters");
        }

        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        StringBuilder leftHalf = new StringBuilder();
        int halfLength = stringLength / 2;

        for (int i = 0; i < halfLength; i++) {
            leftHalf.append(chars[i % chars.length]);
        }

        StringBuilder result = new StringBuilder(stringLength);
        result.append(leftHalf);

        if (stringLength % 2 != 0) {
            result.append(chars[halfLength % chars.length]);
        }

        result.append(new StringBuilder(leftHalf).reverse());

        return result.toString();
    }
}
