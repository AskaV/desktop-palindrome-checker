package com.aska.palindrom.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PalindromeCheckerTest {

    PalindromeChecker palindromeChecker;

    @BeforeEach
    void setUp() {
        palindromeChecker = new PalindromeChecker();
    }

    @Test
    @DisplayName("Return false for empty string")
    void testEmptyString() {
        assertFalse(palindromeChecker.isPalindrome(""));
    }

    @Test
    @DisplayName("Return false for empty string")
    void testSpaceCharacter() {
        assertTrue(palindromeChecker.isPalindrome(" "));
    }

    @Test
    @DisplayName("Return false for null input")
    void testNullInput() {
        assertFalse(palindromeChecker.isPalindrome(null));
    }

    @Test
    @DisplayName("Return true for single character")
    void testSingleCharacter() {
        assertTrue(palindromeChecker.isPalindrome("a"));
    }

    @Test
    @DisplayName("Return true for two equal characters")
    void testEqualCharacters() {
        assertTrue(palindromeChecker.isPalindrome("aa"));
    }

    @Test
    @DisplayName("Return false for two different characters")
    void testDifferentCharacters() {
        assertFalse(palindromeChecker.isPalindrome("ab"));
    }

    @Test
    @DisplayName("Return true for odd-length palindrome")
    void testOddPalindrome() {
        assertTrue(palindromeChecker.isPalindrome("aba"));
    }

    @Test
    @DisplayName("Return false for two similar characters with different case")
    void testSimilarCharactersDifferentCase() {
        assertFalse(palindromeChecker.isPalindrome("aA"));
    }
}
