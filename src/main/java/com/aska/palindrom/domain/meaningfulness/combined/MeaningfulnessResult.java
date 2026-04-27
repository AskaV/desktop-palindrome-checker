package com.aska.palindrom.domain.meaningfulness.combined;

import com.aska.palindrom.domain.meaningfulness.TokenAnalysisRow;
import java.util.List;

public record MeaningfulnessResult(
        boolean meaningful, double score, String explanation, List<TokenAnalysisRow> tokenRows) {}
