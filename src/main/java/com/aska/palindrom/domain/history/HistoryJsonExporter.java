package com.aska.palindrom.domain.history;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class HistoryJsonExporter {

    public void export(List<HistoryEntry> entries, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("[");
            writer.newLine();

            for (int i = 0; i < entries.size(); i++) {
                HistoryEntry entry = entries.get(i);

                writer.write("  {");
                writer.newLine();
                writer.write("    \"timestamp\": " + toJson(entry.timestamp()) + ",");
                writer.newLine();
                writer.write("    \"inputPreview\": " + toJson(entry.fullInput()) + ",");
                writer.newLine();
                writer.write("    \"palindromeResult\": " + toJson(entry.palindromeResult()) + ",");
                writer.newLine();
                writer.write(
                        "    \"meaningfulnessResult\": "
                                + toJson(entry.meaningfulnessResult())
                                + ",");
                writer.newLine();
                writer.write("    \"scoreText\": " + toJson(entry.scoreText()));
                writer.newLine();
                writer.write("  }");

                if (i < entries.size() - 1) {
                    writer.write(",");
                }

                writer.newLine();
            }

            writer.write("]");
            writer.newLine();
        }
    }

    private String toJson(String value) {
        if (value == null) {
            return "null";
        }

        return "\""
                + value.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                        .replace("\t", "\\t")
                + "\"";
    }
}
