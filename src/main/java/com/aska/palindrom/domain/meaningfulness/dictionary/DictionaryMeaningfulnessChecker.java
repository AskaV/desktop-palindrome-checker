package com.aska.palindrom.domain.meaningfulness.dictionary;

import com.aska.palindrom.domain.meaningfulness.EnglishWordList;
import com.aska.palindrom.domain.meaningfulness.MeaningfulnessAnalysisSupport;
import java.util.List;

public class DictionaryMeaningfulnessChecker {
    private static final double MIN_MEANINGFUL_PERCENT = 60.0;

    private final EnglishWordList dictionary = new EnglishWordList();
    private final MeaningfulnessAnalysisSupport support = new MeaningfulnessAnalysisSupport();

    public DictionaryMeaningfulnessResult check(String text) {
        try {
            List<String> tokens = support.requireTokens(text);

            int matchedWords = countDictionaryMatches(tokens);
            double score = (double) matchedWords / tokens.size() * 100.0;
            boolean meaningful = score >= MIN_MEANINGFUL_PERCENT;

            String explanation =
                    "Dictionary match: "
                            + matchedWords
                            + "/"
                            + tokens.size()
                            + " words ("
                            + support.roundScore(score)
                            + "%).";

            return new DictionaryMeaningfulnessResult(
                    meaningful, support.roundScore(score), explanation);
        } catch (IllegalArgumentException e) {
            return new DictionaryMeaningfulnessResult(false, 0.0, e.getMessage());
        }
    }

    private int countDictionaryMatches(List<String> tokens) {
        int matches = 0;

        for (String token : tokens) {
            if (dictionary.contains(token)) {
                matches++;
            }
        }

        return matches;
    }
}
