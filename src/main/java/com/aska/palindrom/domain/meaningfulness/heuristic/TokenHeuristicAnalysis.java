package com.aska.palindrom.domain.meaningfulness.heuristic;

public record TokenHeuristicAnalysis(
        boolean hasVowel,
        boolean noLongConsonantSequence,
        boolean noRepeatedCharacterSequence,
        boolean noRarePattern,
        boolean reasonableLength,
        double score) {}
