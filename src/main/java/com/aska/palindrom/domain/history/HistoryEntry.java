package com.aska.palindrom.domain.history;

public record HistoryEntry(
        String timestamp,
        String inputPreview,
        String palindromeResult,
        String meaningfulnessResult,
        String scoreText) {}
