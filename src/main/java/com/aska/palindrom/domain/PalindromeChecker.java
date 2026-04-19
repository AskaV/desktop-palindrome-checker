package com.aska.palindrom.domain;

import static com.aska.palindrom.presentation.logging.AppLogger.LOGGER;

import com.aska.palindrom.domain.settings.NormalizationSettings;

public class PalindromeChecker {
    private final TextNormalizer textNormalizer = new TextNormalizer();

    public boolean isPalindrome(String text) {
        return isPalindrome(text, NormalizationSettings.disabled());
    }

    public boolean isPalindrome(String text, NormalizationSettings options) {
        LOGGER.info("Start palindrome checking logic");

        String normalizedText = textNormalizer.normalize(text, options);
        if (normalizedText == null || normalizedText.equals("")) {
            return false;
        }
        int left = 0;
        int right = normalizedText.length() - 1;

        while (left < right) {
            if (normalizedText.charAt(left) != normalizedText.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
