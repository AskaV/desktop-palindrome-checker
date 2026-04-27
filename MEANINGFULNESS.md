# Meaningfulness Analysis

## Purpose
The meaningfulness checker estimates whether an input word or phrase looks like real English text rather than a random sequence of characters.

It produces:
- a result: meaningful / not meaningful
- a numeric score
- a short explanation
- a token-level breakdown in the UI

## Signals
The checker uses two independent signals.

### 1. Dictionary-based signal
Each token is checked against a bundled offline English word list.

- found in dictionary -> 100%
- not found -> 0%

The dictionary score for the full input is the average across all tokens.

### 2. Heuristic signal
Each token is also checked against five simple rules:
1. contains a vowel
2. does not contain a long consonant run
3. does not contain a long repeated-character run
4. does not contain rare suspicious letter patterns
5. has a reasonable length

Each passed rule contributes 20%.
The heuristic score for the full input is the average across all tokens.

## Final score
The final score combines both signals:

`final score = dictionary score × 0.8 + heuristic score × 0.2`

The dictionary signal has a higher weight because it is the stronger indicator of real English words.
The heuristic signal is weaker and is used as additional evidence.

## Why a dictionary word may still score below 100%
A word found in the dictionary does not automatically receive a perfect final score.

This is intentional because the checker uses two independent signals:
- dictionary presence
- surface-form heuristic analysis

Example: `nightly`

`nightly` is a real English word and is found in the dictionary, so it gets a positive dictionary contribution.
However, it may still lose points in the heuristic part because the consonant-run rule treats one of its consonant sequences as suspicious.

This does not mean the word is incorrect.
It means the heuristic is intentionally simple and may penalize some valid English words.

The checker still applies heuristic rules even to dictionary words because the two signals were designed to stay independent.
If heuristic analysis were skipped for dictionary words, the second signal would become much less meaningful.

## Resources
The checker works fully offline.

Dictionary file:
`src/main/resources/dictionary/english_words.txt`

Source:
- `github.com/dwyl/english-words`
- license: Unlicense

## Limitations
This implementation is intentionally simple.

Known limitations:
- it is English-focused
- some real words may lose points because of heuristic rules
- some random-looking strings may still pass a few heuristic checks

## Summary
The checker combines:
- dictionary lookup
- heuristic token analysis
- weighted scoring
- explainable per-token output

This keeps the implementation offline, transparent, and easy to test.