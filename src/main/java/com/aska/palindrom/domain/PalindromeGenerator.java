package com.aska.palindrom.domain;

public class PalindromeGenerator {

    public String generate(int blockSize) {
        if (blockSize <= 0) {
            throw new IllegalArgumentException("Block size must be greater than zero");
        }

        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        StringBuilder sb = new StringBuilder(blockSize * chars.length * 2);

        for (char ch : chars) {
            for (int i = 0; i < blockSize; i++) {
                sb.append(ch);
            }
        }

        for (int i = chars.length - 1; i >= 0; i--) {
            for (int j = 0; j < blockSize; j++) {
                sb.append(chars[i]);
            }
        }

        return sb.toString();
    }
}
