package com.aska.palindrom.domain;

import com.aska.palindrom.domain.settings.NormalizationSettings;
import java.text.Normalizer;

public class TextNormalizer {

    public String normalize(String text, NormalizationSettings options) {
        if (text == null) {
            return "";
        }

        String normalizedText = text;

        if (options.ignoreCase()) {
            normalizedText = normalizedText.toLowerCase();
        }

        if (options.ignoreSpaces()) {
            normalizedText = normalizedText.replaceAll("\\s+", "");
        }
        if (options.ignorePunctuation()) {
            normalizedText = normalizedText.replaceAll("[^\\p{L}\\p{N}]", "");
        }

        if (options.unicodeNormalization()) {
            normalizedText = Normalizer.normalize(normalizedText, Normalizer.Form.NFKC);
        }

        if (options.ignoreDiacritics()) {
            normalizedText = Normalizer.normalize(normalizedText, Normalizer.Form.NFD);
            normalizedText = normalizedText.replaceAll("\\p{M}", "");
        }

        return normalizedText;
    }
}
