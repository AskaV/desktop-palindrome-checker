package com.aska.palindrom.domain.meaningfulness.dictionary;

public record DictionaryMeaningfulnessResult(
        boolean meaningful, double score, String explanation) {}
