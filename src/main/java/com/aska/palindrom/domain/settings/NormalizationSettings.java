package com.aska.palindrom.domain.settings;

public record NormalizationSettings(
        boolean ignoreCase,
        boolean ignoreSpaces,
        boolean ignorePunctuation,
        boolean unicodeNormalization,
        boolean ignoreDiacritics) {}
