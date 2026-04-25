package com.aska.palindrom.domain.meaningfulness;

public record DictionaryMeaningfulnessResult(
        boolean meaningful, double score, String explanation) {}
