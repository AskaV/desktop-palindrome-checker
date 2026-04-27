package com.aska.palindrom.domain.meaningfulness;

import java.util.List;

public class MeaningfulnessAnalysisSupport {
    private final MeaningfulnessTokenizer tokenizer = new MeaningfulnessTokenizer();

    public List<String> requireTokens(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Input is empty.");
        }

        List<String> tokens = tokenizer.tokenize(text);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("No valid word tokens were found.");
        }

        return tokens;
    }

    public double roundScore(double score) {
        return Math.round(score * 10.0) / 10.0;
    }
}
