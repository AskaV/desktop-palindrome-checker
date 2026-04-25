package com.aska.palindrom.domain.meaningfulness.heuristic;

import com.aska.palindrom.domain.meaningfulness.MeaningfulnessAnalysisSupport;
import java.util.List;
import java.util.Set;

public class RandomSequenceHeuristicChecker {
    private static final double MIN_NATURAL_SCORE = 60.0;
    static final int HEURISTIC_CHECK_COUNT = 5;
    private static final double CHECK_WEIGHT = 100.0 / HEURISTIC_CHECK_COUNT;

    private static final Set<Character> VOWELS = Set.of('a', 'e', 'i', 'o', 'u', 'y');

    private final MeaningfulnessAnalysisSupport support = new MeaningfulnessAnalysisSupport();

    public RandomSequenceResult check(String text) {
        try {
            List<String> tokens = support.requireTokens(text);

            double totalScore = 0.0;

            for (String token : tokens) {
                totalScore += scoreToken(token);
            }

            double finalScore = totalScore / tokens.size();
            boolean naturalLooking = finalScore >= MIN_NATURAL_SCORE;

            String explanation =
                    "Random-sequence heuristic score: " + support.roundScore(finalScore) + "%.";

            return new RandomSequenceResult(
                    naturalLooking, support.roundScore(finalScore), explanation);
        } catch (IllegalArgumentException e) {
            return new RandomSequenceResult(false, 0.0, e.getMessage());
        }
    }

    private double scoreToken(String token) {
        double score = 0.0;

        if (hasVowel(token)) {
            score += CHECK_WEIGHT;
        }

        if (!hasLongConsonantSequence(token, 4)) {
            score += CHECK_WEIGHT;
        }

        if (!hasRepeatedCharacterSequence(token, 4)) {
            score += CHECK_WEIGHT;
        }

        if (!containsRarePattern(token)) {
            score += CHECK_WEIGHT;
        }

        if (hasReasonableLength(token)) {
            score += CHECK_WEIGHT;
        }

        return score;
    }

    private boolean hasVowel(String token) {
        for (char ch : token.toCharArray()) {
            if (VOWELS.contains(ch)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasLongConsonantSequence(String token, int minLength) {
        int count = 0;

        for (char ch : token.toCharArray()) {
            if (Character.isLetter(ch) && !VOWELS.contains(ch)) {
                count++;
                if (count >= minLength) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        return false;
    }

    private boolean hasRepeatedCharacterSequence(String token, int minLength) {
        int count = 1;

        for (int i = 1; i < token.length(); i++) {
            if (token.charAt(i) == token.charAt(i - 1)) {
                count++;
                if (count >= minLength) {
                    return true;
                }
            } else {
                count = 1;
            }
        }

        return false;
    }

    private boolean containsRarePattern(String token) {
        return token.contains("qx")
                || token.contains("qz")
                || token.contains("zx")
                || token.contains("jj")
                || token.contains("vvv");
    }

    private boolean hasReasonableLength(String token) {
        return token.length() >= 2 && token.length() <= 20;
    }
}
