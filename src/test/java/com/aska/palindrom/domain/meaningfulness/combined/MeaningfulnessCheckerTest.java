package com.aska.palindrom.domain.meaningfulness.combined;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MeaningfulnessCheckerTest {
    private final MeaningfulnessChecker checker = new MeaningfulnessChecker();

    /**
     * Empty input is invalid for both dictionary and heuristic analysis. The combined checker
     * should return score 0.0 and the shared explanation.
     */
    @Test
    @DisplayName("Return score 0.0 for empty input")
    void shouldReturnNotMeaningfulForEmptyInput() {
        MeaningfulnessResult result = checker.check("");

        assertFalse(result.meaningful());
        assertEquals(0.0, result.score());
        assertEquals("Input is empty.", result.explanation());
    }

    /**
     * "hello world" gets: - dictionary score 100.0 - heuristic score 100.0
     *
     * <p>Final score: 100.0 * 0.8 + 100.0 * 0.2 = 100.0
     */
    @Test
    @DisplayName("Return 100.0% for phrase 'hello world'")
    void shouldReturnHighScoreForHelloWorld() {
        MeaningfulnessResult result = checker.check("hello world");

        assertTrue(result.meaningful());
        assertEquals(100.0, result.score());
        assertEquals(
                "Dictionary score: 100.0%. Heuristic score: 100.0%. Final score: 100.0%.",
                result.explanation());
    }

    /**
     * "hello xqzplm" gets: - dictionary score 50.0 - heuristic score 70.0
     *
     * <p>Final score: 50.0 * 0.8 + 70.0 * 0.2 = 54.0
     */
    @Test
    @DisplayName("Return 54.0% for mixed phrase")
    void shouldReturnMixedScore() {
        MeaningfulnessResult result = checker.check("hello xqzplm");

        assertFalse(result.meaningful());
        assertEquals(54.0, result.score());
        assertEquals(
                "Dictionary score: 50.0%. Heuristic score: 70.0%. Final score: 54.0%.",
                result.explanation());
    }

    /**
     * "xqzplm blrfk" gets: - dictionary score 0.0 - heuristic score below threshold
     *
     * <p>Final score should stay below 60.0.
     */
    @Test
    @DisplayName("Return low score for random-looking phrase")
    void shouldReturnLowScoreForRandomPhrase() {
        MeaningfulnessResult result = checker.check("xqzplm blrfk");

        assertFalse(result.meaningful());
        assertTrue(result.score() < 60.0);
    }
}
