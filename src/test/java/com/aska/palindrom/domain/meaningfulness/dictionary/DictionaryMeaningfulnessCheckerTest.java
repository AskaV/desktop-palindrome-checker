package com.aska.palindrom.domain.meaningfulness.dictionary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DictionaryMeaningfulnessCheckerTest {

    private final DictionaryMeaningfulnessChecker checker = new DictionaryMeaningfulnessChecker();

    @Test
    @DisplayName("Return not meaningful for empty input")
    void shouldReturnNotMeaningfulForEmptyInput() {
        DictionaryMeaningfulnessResult result = checker.check("");

        assertFalse(result.meaningful());
        assertEquals(0.0, result.score());
    }

    @Test
    @DisplayName("Return meaningful for phrase hello world")
    void shouldReturnMeaningfulForHelloWorld() {
        DictionaryMeaningfulnessResult result = checker.check("hello world");

        assertTrue(result.meaningful());
        assertEquals(100.0, result.score());
        assertEquals("Dictionary match: 2/2 words (100.0%).", result.explanation());
    }

    @Test
    @DisplayName("Return meaningful for phrase Hello World regardless of case")
    void shouldReturnMeaningfulForHelloWorldIgnoringCase() {
        DictionaryMeaningfulnessResult result = checker.check("Hello World");

        assertTrue(result.meaningful());
        assertEquals(100.0, result.score());
        assertEquals("Dictionary match: 2/2 words (100.0%).", result.explanation());
    }

    @Test
    @DisplayName("Return not meaningful for phrase with one known and one unknown word")
    void shouldReturnNotMeaningfulForMixedPhrase() {
        DictionaryMeaningfulnessResult result = checker.check("hello xqzplm");

        assertFalse(result.meaningful());
        assertEquals(50.0, result.score());
        assertEquals("Dictionary match: 1/2 words (50.0%).", result.explanation());
    }
}
