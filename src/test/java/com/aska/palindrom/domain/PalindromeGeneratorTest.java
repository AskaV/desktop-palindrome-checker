package com.aska.palindrom.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalindromeGeneratorTest {

    private PalindromeGenerator palindromeGenerator;
    private PalindromeChecker palindromeChecker;

    @BeforeEach
    void setUp() {
        palindromeGenerator = new PalindromeGenerator();
        palindromeChecker = new PalindromeChecker();
    }

    @Test
    @DisplayName("Check palindrome with block size 100000")
    void test100000CharsString() {
        String result = palindromeGenerator.generate(100000);

        assertNotNull(result);
        assertEquals(1000000, result.length());
        assertTrue(palindromeChecker.isPalindrome(result));
    }

    @Test
    @DisplayName("Check palindrome with block size 1000000")
    void test1000000CharsString() {
        String result = palindromeGenerator.generate(1000000);

        assertNotNull(result);
        assertEquals(10000000, result.length());
        assertTrue(palindromeChecker.isPalindrome(result));
    }

    @Test
    @DisplayName("Check non-palindrome when extra character is added")
    void test1000001CharsStringNegative() {
        String result = palindromeGenerator.generate(1000000);
        result = result + "a";

        assertNotNull(result);
        assertEquals(10000001, result.length());
        assertFalse(palindromeChecker.isPalindrome(result));
    }
}
