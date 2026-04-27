package com.aska.palindrom.domain.history;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HistoryJsonStorage {
    private final HistoryJsonExporter exporter = new HistoryJsonExporter();

    public void save(List<HistoryEntry> entries, Path path) throws IOException {
        exporter.export(entries, path);
    }

    public List<HistoryEntry> load(Path path) throws IOException {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        String content = Files.readString(path);
        return parseEntries(content);
    }

    private List<HistoryEntry> parseEntries(String content) {
        List<HistoryEntry> result = new ArrayList<>();

        String trimmed = content.trim();
        if (trimmed.isEmpty() || trimmed.equals("[]")) {
            return result;
        }

        String body = trimmed.substring(1, trimmed.length() - 1).trim();
        if (body.isEmpty()) {
            return result;
        }

        String[] objects = body.split("\\},\\s*\\{");

        for (String object : objects) {
            String normalized = object.trim();

            if (!normalized.startsWith("{")) {
                normalized = "{" + normalized;
            }
            if (!normalized.endsWith("}")) {
                normalized = normalized + "}";
            }

            String timestamp = extractJsonValue(normalized, "timestamp");
            String fullInput = extractJsonValue(normalized, "fullInput");
            String palindromeResult = extractJsonValue(normalized, "palindromeResult");
            String meaningfulnessResult = extractJsonValue(normalized, "meaningfulnessResult");
            String scoreText = extractJsonValue(normalized, "scoreText");

            if (timestamp.isEmpty() || fullInput.isEmpty()) {
                throw new IllegalArgumentException("Invalid history JSON structure");
            }

            result.add(
                    new HistoryEntry(
                            timestamp,
                            fullInput,
                            palindromeResult,
                            meaningfulnessResult,
                            scoreText));
        }

        return result;
    }

    private String extractJsonValue(String object, String key) {
        String pattern = "\"" + key + "\":";
        int keyIndex = object.indexOf(pattern);
        if (keyIndex < 0) {
            return "";
        }

        int valueStart = object.indexOf('"', keyIndex + pattern.length());
        if (valueStart < 0) {
            return "";
        }

        int valueEnd = valueStart + 1;
        boolean escaped = false;

        while (valueEnd < object.length()) {
            char ch = object.charAt(valueEnd);

            if (ch == '"' && !escaped) {
                break;
            }

            escaped = ch == '\\' && !escaped;
            if (ch != '\\') {
                escaped = false;
            }

            valueEnd++;
        }

        String raw = object.substring(valueStart + 1, valueEnd);
        return raw.replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }
}
