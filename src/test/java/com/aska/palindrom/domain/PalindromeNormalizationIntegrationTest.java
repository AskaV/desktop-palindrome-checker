package com.aska.palindrom.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.aska.palindrom.domain.settings.NormalizationSettings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PalindromeNormalizationIntegrationTest {

    private final TextNormalizer textNormalizer = new TextNormalizer();
    private final PalindromeChecker palindromeChecker = new PalindromeChecker();

    @Test
    @DisplayName("Recognize palindrome for A man, a plan, a canal: Panama after normalization")
    void shouldRecognizePalindromeForPanamaPhraseAfterNormalization() {
        String normalized =
                textNormalizer.normalize(
                        "A man, a plan, a canal: Panama!",
                        new NormalizationSettings(true, true, true, false, false));

        assertTrue(palindromeChecker.isPalindrome(normalized));
    }

    @Test
    @DisplayName("Recognize palindrome for Madam, I'm Adam after normalization")
    void shouldRecognizePalindromeForMadamImAdamAfterNormalization() {
        String normalized =
                textNormalizer.normalize(
                        "Madam, I'm Adam", new NormalizationSettings(true, true, true, true, true));

        assertTrue(palindromeChecker.isPalindrome(normalized));
    }
}
