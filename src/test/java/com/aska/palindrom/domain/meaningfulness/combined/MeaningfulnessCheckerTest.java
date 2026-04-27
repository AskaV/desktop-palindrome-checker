package com.aska.palindrom.domain.meaningfulness.combined;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MeaningfulnessCheckerTest {
    private final MeaningfulnessChecker checker = new MeaningfulnessChecker();

    @Test
    @DisplayName("Return score 0.0 for empty input")
    void shouldReturnNotMeaningfulForEmptyInput() {
        MeaningfulnessResult result = checker.check("");

        assertFalse(result.meaningful());
        assertEquals(0.0, result.score());
        assertEquals("Input is empty.", result.explanation());
    }

    @Test
    @DisplayName("Return 100.0% for phrase 'hello world'")
    void shouldReturnHighScoreForHelloWorld() {
        MeaningfulnessResult result = checker.check("hello world");

        assertTrue(result.meaningful());
        assertEquals(100.0, result.score());
        assertEquals(
                "Dictionary-based score is 100.0%, heuristic score is 100.0%.",
                result.explanation());
    }

    @Test
    @DisplayName("Return 54.0% for mixed phrase")
    void shouldReturnMixedScore() {
        MeaningfulnessResult result = checker.check("hello xqzplm");

        assertFalse(result.meaningful());
        assertEquals(54.0, result.score());
        assertEquals(
                "Dictionary-based score is 50.0%, heuristic score is 70.0%.", result.explanation());
    }

    @Test
    @DisplayName("Return low score for random-looking phrase")
    void shouldReturnLowScoreForRandomPhrase() {
        MeaningfulnessResult result = checker.check("xqzplm blrfk");

        assertFalse(result.meaningful());
        assertTrue(result.score() < 60.0);
        assertEquals(
                "Dictionary-based score is 0.0%, heuristic score is 50.0%.", result.explanation());
    }
}
