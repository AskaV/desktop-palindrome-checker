package com.aska.palindrom.domain.meaningfulness.heuristic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RandomSequenceHeuristicCheckerTest {
    private static final int CHECK_COUNT = 5;

    private final RandomSequenceHeuristicChecker checker = new RandomSequenceHeuristicChecker();

    /**
     * Empty input is treated as invalid for heuristic analysis. The checker should return score 0.0
     * and the corresponding explanation.
     */
    @Test
    @DisplayName("Return score 0.0 for empty input")
    void shouldReturnNotNaturalForEmptyInput() {
        RandomSequenceResult result = checker.check("");

        assertFalse(result.naturalLooking());
        assertEquals(0.0, result.score());
        assertEquals("Input is empty.", result.explanation());
    }

    /**
     * "hello" passes all 5 heuristic checks: 1) has vowel 2) no long consonant sequence 3) no
     * repeated character sequence 4) no rare pattern 5) reasonable length
     */
    @Test
    @DisplayName("Return 100.0% for natural-looking word")
    void shouldReturnHighScoreForNaturalLookingWord() {
        RandomSequenceResult result = checker.check("hello");

        assertTrue(result.naturalLooking());
        assertEquals(expectedScore(5, CHECK_COUNT), result.score());
        assertEquals("Random-sequence heuristic score: 100.0%.", result.explanation());
    }

    /**
     * "xqzplm" passes only 2 heuristic checks: 1) no repeated character sequence 2) reasonable
     * length
     *
     * <p>It fails: - has vowel - no long consonant sequence - no rare pattern
     */
    @Test
    @DisplayName("Return 40.0% for token without vowels")
    void shouldReturnLowScoreForTokenWithoutVowels() {
        RandomSequenceResult result = checker.check("xqzplm");

        assertFalse(result.naturalLooking());
        assertEquals(expectedScore(2, CHECK_COUNT), result.score());
        assertEquals("Random-sequence heuristic score: 40.0%.", result.explanation());
    }

    /**
     * "zzzzzz" passes only 2 heuristic checks: 1) no rare pattern 2) reasonable length
     *
     * <p>It fails: - has vowel - no long consonant sequence - no repeated character sequence
     */
    @Test
    @DisplayName("Return 40.0% for repeated characters")
    void shouldReturnLowScoreForRepeatedCharacters() {
        RandomSequenceResult result = checker.check("zzzzzz");

        assertFalse(result.naturalLooking());
        assertEquals(expectedScore(2, CHECK_COUNT), result.score());
        assertEquals("Random-sequence heuristic score: 40.0%.", result.explanation());
    }

    /**
     * "hello world" is tokenized into two natural-looking words. Both tokens pass all 5 heuristic
     * checks, so the average score is 100.0%.
     */
    @Test
    @DisplayName("Return 100.0% for simple phrase")
    void shouldReturnHighScoreForSimplePhrase() {
        RandomSequenceResult result = checker.check("hello world");

        assertTrue(result.naturalLooking());
        assertEquals(expectedScore(5, CHECK_COUNT), result.score());
        assertEquals("Random-sequence heuristic score: 100.0%.", result.explanation());
    }

    /**
     * "hello xqzplm" is tokenized into: - "hello" -> 100.0% - "xqzplm" -> 40.0%
     *
     * <p>The checker averages token scores across the phrase: (100.0 + 40.0) / 2 = 70.0
     */
    @Test
    @DisplayName("Return averaged score for mixed phrase")
    void shouldReturnAverageScoreForMixedPhrase() {
        RandomSequenceResult result = checker.check("hello xqzplm");

        double helloScore = expectedScore(5, CHECK_COUNT);
        double randomScore = expectedScore(2, CHECK_COUNT);
        double expectedAverage = roundScore((helloScore + randomScore) / 2.0);

        assertTrue(result.naturalLooking());
        assertEquals(expectedAverage, result.score());
        assertEquals("Random-sequence heuristic score: 70.0%.", result.explanation());
    }

    /** Calculates the expected heuristic score based on the number of passed checks. */
    private double expectedScore(int passedChecks, int totalChecks) {
        return roundScore(passedChecks * (100.0 / totalChecks));
    }

    /** Rounds a score to one decimal place. */
    private double roundScore(double score) {
        return Math.round(score * 10.0) / 10.0;
    }
}
