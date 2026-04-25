package com.aska.palindrom.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.aska.palindrom.domain.meaningfulness.EnglishDictionary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnglishDictionaryTest {

    private final EnglishDictionary dictionary = new EnglishDictionary();

    @Test
    @DisplayName("Contain common English word")
    void shouldContainCommonEnglishWord() {
        assertTrue(dictionary.contains("hello"));
    }

    @Test
    @DisplayName("Contain common English word regardless of case")
    void shouldContainWordIgnoringCase() {
        assertTrue(dictionary.contains("Hello"));
    }

    @Test
    @DisplayName("Return false for random token")
    void shouldReturnFalseForRandomToken() {
        assertFalse(dictionary.contains("xqzplm"));
    }
}
