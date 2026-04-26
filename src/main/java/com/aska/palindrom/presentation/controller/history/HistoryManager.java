package com.aska.palindrom.presentation.controller.history;

import static com.aska.palindrom.presentation.logging.AppLogger.LOGGER;

import com.aska.palindrom.domain.history.HistoryCsvExporter;
import com.aska.palindrom.domain.history.HistoryEntry;
import com.aska.palindrom.domain.history.HistoryJsonExporter;
import com.aska.palindrom.domain.history.HistoryJsonStorage;
import com.aska.palindrom.domain.meaningfulness.combined.MeaningfulnessResult;
import com.aska.palindrom.presentation.panel.EditorPanel;
import com.aska.palindrom.presentation.panel.ResultPanel;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HistoryManager {
    private static final int HISTORY_PREVIEW_LENGTH = 40;

    private final EditorPanel editorPanel;
    private final ResultPanel resultPanel;
    private final ResourceBundle bundle;

    private final HistoryCsvExporter historyCsvExporter = new HistoryCsvExporter();
    private final HistoryJsonExporter historyJsonExporter = new HistoryJsonExporter();
    private final HistoryJsonStorage historyJsonStorage = new HistoryJsonStorage();

    private final Path historyStoragePath = Path.of("history.json");
    private final List<HistoryEntry> historyEntries = new ArrayList<>();

    public HistoryManager(EditorPanel editorPanel, ResultPanel resultPanel, ResourceBundle bundle) {
        this.editorPanel = editorPanel;
        this.resultPanel = resultPanel;
        this.bundle = bundle;
    }

    public void loadHistory() {
        try {
            List<HistoryEntry> loadedEntries = historyJsonStorage.load(historyStoragePath);
            historyEntries.clear();
            historyEntries.addAll(loadedEntries);

            for (HistoryEntry entry : loadedEntries) {
                editorPanel
                        .getHistoryPanel()
                        .addHistoryEntry(
                                entry.timestamp(),
                                entry.inputPreview(),
                                entry.palindromeResult(),
                                entry.meaningfulnessResult(),
                                entry.scoreText());
            }

            LOGGER.info("History loaded successfully");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to load history", e);
            resultPanel.showError(bundle.getString("history.load.error"));
        }
    }

    public void addEntry(
            String inputText, boolean isPalindrome, MeaningfulnessResult meaningfulnessResult) {

        String timestamp =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String inputPreview = buildInputPreview(inputText);

        String palindromeResult =
                isPalindrome
                        ? bundle.getString("history.value.palindrome")
                        : bundle.getString("history.value.notPalindrome");

        String meaningfulnessResultText =
                meaningfulnessResult.meaningful()
                        ? bundle.getString("history.value.meaningful")
                        : bundle.getString("history.value.notMeaningful");

        String scoreText = meaningfulnessResult.score() + "%";

        HistoryEntry entry =
                new HistoryEntry(
                        timestamp,
                        inputPreview,
                        palindromeResult,
                        meaningfulnessResultText,
                        scoreText);

        historyEntries.add(entry);
        saveHistorySilently();

        editorPanel
                .getHistoryPanel()
                .addHistoryEntry(
                        entry.timestamp(),
                        entry.inputPreview(),
                        entry.palindromeResult(),
                        entry.meaningfulnessResult(),
                        entry.scoreText());
    }

    public void exportCsv() {
        LOGGER.info("Export CSV button clicked");
        resultPanel.showError("");

        if (historyEntries.isEmpty()) {
            resultPanel.showError(bundle.getString("history.export.empty"));
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(bundle.getString("history.export.dialogTitle"));
        fileChooser.setSelectedFile(new java.io.File("history.csv"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));

        int result = fileChooser.showSaveDialog(editorPanel);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path filePath = fileChooser.getSelectedFile().toPath();
        if (!filePath.toString().toLowerCase().endsWith(".csv")) {
            filePath = Path.of(filePath.toString() + ".csv");
        }

        try {
            historyCsvExporter.export(historyEntries, filePath);
            LOGGER.info("History exported to CSV: " + filePath);
            resultPanel.showSuccessMessage(bundle.getString("history.export.success"));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to export history to CSV", e);
            resultPanel.showError(bundle.getString("history.export.error"));
        }
    }

    public void exportJson() {
        LOGGER.info("Export JSON button clicked");
        resultPanel.showError("");

        if (historyEntries.isEmpty()) {
            resultPanel.showError(bundle.getString("history.export.empty"));
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(bundle.getString("history.export.jsonDialogTitle"));
        fileChooser.setSelectedFile(new java.io.File("history.json"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));

        int result = fileChooser.showSaveDialog(editorPanel);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path filePath = fileChooser.getSelectedFile().toPath();
        if (!filePath.toString().toLowerCase().endsWith(".json")) {
            filePath = Path.of(filePath.toString() + ".json");
        }

        try {
            historyJsonExporter.export(historyEntries, filePath);
            LOGGER.info("History exported to JSON: " + filePath);
            resultPanel.showSuccessMessage(bundle.getString("history.export.successJson"));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to export history to JSON", e);
            resultPanel.showError(bundle.getString("history.export.error"));
        }
    }

    public void clearHistory() {
        LOGGER.info("Clear history button clicked");

        historyEntries.clear();
        editorPanel.getHistoryPanel().clear();

        try {
            historyJsonStorage.save(historyEntries, historyStoragePath);
            resultPanel.showSuccessMessage(bundle.getString("history.clear.success"));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to clear history", e);
            resultPanel.showError(bundle.getString("history.clear.error"));
        }
    }

    private void saveHistorySilently() {
        try {
            historyJsonStorage.save(historyEntries, historyStoragePath);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to save history", e);
            resultPanel.showError(bundle.getString("history.save.error"));
        }
    }

    private String buildInputPreview(String text) {
        String singleLine = text.replaceAll("\\s+", " ").trim();

        if (singleLine.length() <= HISTORY_PREVIEW_LENGTH) {
            return singleLine;
        }

        return singleLine.substring(0, HISTORY_PREVIEW_LENGTH) + "...";
    }
}
