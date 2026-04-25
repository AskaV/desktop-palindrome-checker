package com.aska.palindrom.domain.meaningfulness.combined;

import com.aska.palindrom.domain.meaningfulness.MeaningfulnessAnalysisSupport;
import com.aska.palindrom.domain.meaningfulness.dictionary.DictionaryMeaningfulnessChecker;
import com.aska.palindrom.domain.meaningfulness.dictionary.DictionaryMeaningfulnessResult;
import com.aska.palindrom.domain.meaningfulness.heuristic.RandomSequenceHeuristicChecker;
import com.aska.palindrom.domain.meaningfulness.heuristic.RandomSequenceResult;

public class MeaningfulnessChecker {
    private static final double MIN_MEANINGFUL_SCORE = 60.0;
    private static final double DICTIONARY_WEIGHT = 0.8;
    private static final double HEURISTIC_WEIGHT = 0.2;

    private final DictionaryMeaningfulnessChecker dictionaryChecker =
            new DictionaryMeaningfulnessChecker();
    private final RandomSequenceHeuristicChecker heuristicChecker =
            new RandomSequenceHeuristicChecker();
    private final MeaningfulnessAnalysisSupport support = new MeaningfulnessAnalysisSupport();

    public MeaningfulnessResult check(String text) {
        DictionaryMeaningfulnessResult dictionaryResult = dictionaryChecker.check(text);
        RandomSequenceResult heuristicResult = heuristicChecker.check(text);

        if (isInvalidInput(dictionaryResult, heuristicResult)) {
            return new MeaningfulnessResult(false, 0.0, dictionaryResult.explanation());
        }

        double finalScore =
                dictionaryResult.score() * DICTIONARY_WEIGHT
                        + heuristicResult.score() * HEURISTIC_WEIGHT;

        double roundedScore = support.roundScore(finalScore);
        boolean meaningful = roundedScore >= MIN_MEANINGFUL_SCORE;

        String explanation =
                "Dictionary score: "
                        + dictionaryResult.score()
                        + "%. Heuristic score: "
                        + heuristicResult.score()
                        + "%. Final score: "
                        + roundedScore
                        + "%.";

        return new MeaningfulnessResult(meaningful, roundedScore, explanation);
    }

    private boolean isInvalidInput(
            DictionaryMeaningfulnessResult dictionaryResult, RandomSequenceResult heuristicResult) {
        return dictionaryResult.score() == 0.0
                && heuristicResult.score() == 0.0
                && dictionaryResult.explanation().equals(heuristicResult.explanation());
    }
}
