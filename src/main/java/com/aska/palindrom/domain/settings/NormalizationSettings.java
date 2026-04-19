package com.aska.palindrom.domain.settings;

public record NormalizationSettings(
        boolean ignoreCase,
        boolean ignoreSpaces,
        boolean ignorePunctuation,
        boolean unicodeNormalization,
        boolean ignoreDiacritics) {
    public static NormalizationSettings disabled() {
        return new NormalizationSettings(false, false, false, false, false);
    }
}
