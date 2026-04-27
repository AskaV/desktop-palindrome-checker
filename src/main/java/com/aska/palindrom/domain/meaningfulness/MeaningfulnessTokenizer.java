package com.aska.palindrom.domain.meaningfulness;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MeaningfulnessTokenizer {

    public List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return tokens;
        }

        String[] rawTokens = text.toLowerCase(Locale.ROOT).split("\\s+");

        for (String rawToken : rawTokens) {
            String cleanedToken = rawToken.replaceAll("[^\\p{L}]", "");
            if (!cleanedToken.isBlank()) {
                tokens.add(cleanedToken);
            }
        }

        return tokens;
    }
}
