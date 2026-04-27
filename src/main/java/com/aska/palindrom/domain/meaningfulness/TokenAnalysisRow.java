package com.aska.palindrom.domain.meaningfulness;

public record TokenAnalysisRow(
        String token,
        boolean foundInDictionary,
        double dictionaryScore,
        boolean hasVowel,
        boolean noLongConsonantSequence,
        boolean noRepeatedCharacterSequence,
        boolean noRarePattern,
        boolean reasonableLength,
        double heuristicScore) {}
