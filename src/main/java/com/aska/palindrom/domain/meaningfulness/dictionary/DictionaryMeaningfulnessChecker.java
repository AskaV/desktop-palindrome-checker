package com.aska.palindrom.domain.meaningfulness.dictionary;

import com.aska.palindrom.domain.meaningfulness.EnglishDictionary;
import com.aska.palindrom.domain.meaningfulness.MeaningfulnessTokenizer;
import java.util.List;

public class DictionaryMeaningfulnessChecker {
    private static final double MIN_MEANINGFUL_PERCENT = 60.0;

    private final EnglishDictionary dictionary = new EnglishDictionary();
    private final MeaningfulnessTokenizer tokenizer = new MeaningfulnessTokenizer();

    public DictionaryMeaningfulnessResult check(String text) {
        if (text == null || text.isBlank()) {
            return new DictionaryMeaningfulnessResult(false, 0.0, "Input is empty.");
        }

        List<String> tokens = tokenizer.tokenize(text);
        if (tokens.isEmpty()) {
            return new DictionaryMeaningfulnessResult(
                    false, 0.0, "No valid word tokens were found.");
        }

        int matchedWords = countDictionaryMatches(tokens);
        double score = (double) matchedWords / tokens.size() * 100.0;
        boolean meaningful = score >= MIN_MEANINGFUL_PERCENT;

        String explanation =
                "Dictionary match: "
                        + matchedWords
                        + "/"
                        + tokens.size()
                        + " words ("
                        + roundScore(score)
                        + "%).";

        return new DictionaryMeaningfulnessResult(meaningful, roundScore(score), explanation);
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

    private double roundScore(double score) {
        return Math.round(score * 10.0) / 10.0;
    }
}
