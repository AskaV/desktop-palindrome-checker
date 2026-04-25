package com.aska.palindrom.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.aska.palindrom.domain.settings.NormalizationSettings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TextNormalizerTest {
    private final TextNormalizer textNormalizer = new TextNormalizer();

    @Test
    @DisplayName("Return empty string for null input")
    void shouldReturnEmptyStringForNullInput() {
        String result =
                textNormalizer.normalize(
                        null, new NormalizationSettings(false, false, false, false, false));

        assertEquals("", result);
    }

    @Test
    @DisplayName("Do not change text when all normalization options are disabled")
    void shouldNotChangeTextWhenAllOptionsDisabled() {
        String result = textNormalizer.normalize("Abba !", NormalizationSettings.disabled());

        assertEquals("Abba !", result);
    }

    @Test
    @DisplayName("Normalize text ignoring case")
    void shouldNormalizeIgnoringCase() {
        String result =
                textNormalizer.normalize(
                        "AbBa", new NormalizationSettings(true, false, false, false, false));

        assertEquals("abba", result);
    }

    @Test
    @DisplayName("Normalize text ignoring spaces")
    void shouldNormalizeIgnoringSpaces() {
        String result =
                textNormalizer.normalize(
                        "a b   b a", new NormalizationSettings(false, true, false, false, false));

        assertEquals("abba", result);
    }

    @Test
    @DisplayName("Normalize text ignoring punctuation")
    void shouldNormalizeIgnoringPunctuation() {
        String result =
                textNormalizer.normalize(
                        "a!b@b#a$", new NormalizationSettings(false, false, true, false, false));

        assertEquals("abba", result);
    }

    @Test
    @DisplayName("Normalize text with Unicode normalization")
    void shouldNormalizeUnicodeText() {
        String result =
                textNormalizer.normalize(
                        "ＡbｂＡ", new NormalizationSettings(false, false, false, true, false));

        assertEquals("AbbA", result);
    }

    @Test
    @DisplayName("Normalize text ignoring diacritics")
    void shouldNormalizeIgnoringDiacritics() {
        String result =
                textNormalizer.normalize(
                        "café", new NormalizationSettings(false, false, false, false, true));

        assertEquals("cafe", result);
    }

    @Test
    @DisplayName("Apply several normalization rules together")
    void shouldApplySeveralNormalizationRulesTogether() {
        String result =
                textNormalizer.normalize(
                        "A man, a plan, a canal: Panama!",
                        new NormalizationSettings(true, true, true, false, false));

        assertEquals("amanaplanacanalpanama", result);
    }

    @Test
    @DisplayName("Apply all normalization rules together for Madam, I'm Adam")
    void shouldApplyAllNormalizationRulesTogetherForMadamImAdam() {
        String result =
                textNormalizer.normalize(
                        "Madam, I'm Adam", new NormalizationSettings(true, true, true, true, true));

        assertEquals("madamimadam", result);
    }
}
