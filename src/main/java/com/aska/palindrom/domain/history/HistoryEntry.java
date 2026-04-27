package com.aska.palindrom.domain.history;

public record HistoryEntry(
        String timestamp,
        String fullInput,
        String palindromeResult,
        String meaningfulnessResult,
        String scoreText) {}
