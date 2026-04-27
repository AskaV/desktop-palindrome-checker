package com.aska.palindrom.domain.meaningfulness;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class EnglishWordList {
    private static final String DICTIONARY_PATH = "/dictionary/english_words.txt";

    private final Set<String> words;

    public EnglishWordList() {
        this.words = loadWords();
    }

    public boolean contains(String word) {
        if (word == null || word.isBlank()) {
            return false;
        }
        return words.contains(word.toLowerCase());
    }

    public int size() {
        return words.size();
    }

    private Set<String> loadWords() {
        Set<String> loadedWords = new HashSet<>();

        try (InputStream inputStream = getClass().getResourceAsStream(DICTIONARY_PATH)) {
            if (inputStream == null) {
                throw new IllegalStateException("Dictionary file not found: " + DICTIONARY_PATH);
            }

            try (BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String normalizedWord = line.trim().toLowerCase();
                    if (!normalizedWord.isEmpty()) {
                        loadedWords.add(normalizedWord);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load dictionary: " + DICTIONARY_PATH, e);
        }

        return loadedWords;
    }
}
