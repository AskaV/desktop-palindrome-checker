package com.aska.palindrom.domain;

import static com.aska.palindrom.presentation.logging.AppLogger.LOGGER;

public class PalindromeChecker {

    public boolean isPalindrome(String text) {
        LOGGER.info("Start palindrome checking logic");

        if (text == null || text.equals("")) {
            return false;
        }
        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
