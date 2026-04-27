package com.aska.palindrom.domain.history;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class HistoryCsvExporter {

    public void export(List<HistoryEntry> entries, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write('\uFEFF');
            writer.write("Time,Input,Palindrome,Meaningfulness,Score");
            writer.newLine();

            for (HistoryEntry entry : entries) {
                writer.write(
                        escape(entry.timestamp())
                                + ","
                                + escape(entry.fullInput())
                                + ","
                                + escape(entry.palindromeResult())
                                + ","
                                + escape(entry.meaningfulnessResult())
                                + ","
                                + escape(entry.scoreText()));
                writer.newLine();
            }
        }
    }

    private String escape(String value) {
        if (value == null) {
            return "\"\"";
        }

        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}
