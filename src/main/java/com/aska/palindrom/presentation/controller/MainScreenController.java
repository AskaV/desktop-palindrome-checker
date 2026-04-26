package com.aska.palindrom.presentation.controller;

import static com.aska.palindrom.presentation.logging.AppLogger.LOGGER;

import com.aska.palindrom.domain.PalindromeChecker;
import com.aska.palindrom.domain.TextNormalizer;
import com.aska.palindrom.domain.history.HistoryCsvExporter;
import com.aska.palindrom.domain.history.HistoryEntry;
import com.aska.palindrom.domain.meaningfulness.combined.MeaningfulnessChecker;
import com.aska.palindrom.domain.meaningfulness.combined.MeaningfulnessResult;
import com.aska.palindrom.domain.settings.NormalizationSettings;
import com.aska.palindrom.presentation.panel.EditorPanel;
import com.aska.palindrom.presentation.panel.ResultPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainScreenController {
    private static final int MAX_INPUT_LENGTH = 1000000;
    private static final int HISTORY_PREVIEW_LENGTH = 40;

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    private final EditorPanel editorPanel;
    private final ResultPanel resultPanel;
    private final PalindromeChecker palindromeChecker;
    private final MeaningfulnessChecker meaningfulnessChecker;
    private final TextNormalizer textNormalizer = new TextNormalizer();
    private final HistoryCsvExporter historyCsvExporter = new HistoryCsvExporter();

    private final List<HistoryEntry> historyEntries = new ArrayList<>();

    public MainScreenController(
            EditorPanel editorPanel,
            ResultPanel resultPanel,
            PalindromeChecker palindromeChecker,
            MeaningfulnessChecker meaningfulnessChecker) {
        this.editorPanel = editorPanel;
        this.resultPanel = resultPanel;
        this.palindromeChecker = palindromeChecker;
        this.meaningfulnessChecker = meaningfulnessChecker;

        bindActions();
    }

    private void bindActions() {
        editorPanel.getCheckButton().addActionListener(e -> onCheckClicked());
        editorPanel.getClearButton().addActionListener(e -> onClearClicked());
        editorPanel
                .getHistoryPanel()
                .getExportCsvButton()
                .addActionListener(e -> onExportCsvClicked());

        editorPanel
                .getInputArea()
                .addKeyListener(
                        new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                resultPanel.showNotChecked();
                                resultPanel.showError("");
                                editorPanel.clearNormalizedText();
                                editorPanel.getMeaningfulnessPanel().clear();
                            }
                        });
    }

    private void onCheckClicked() {
        LOGGER.info("Check button clicked");

        try {
            String text = editorPanel.getInputArea().getText();

            if (!isValidInput(text)) {
                editorPanel.getMeaningfulnessPanel().clear();
                return;
            }

            String textForPalindromeCheck = prepareTextForCheck(text);
            boolean isPalindrome = palindromeChecker.isPalindrome(textForPalindromeCheck);
            showCheckResult(isPalindrome);

            MeaningfulnessResult meaningfulnessResult = meaningfulnessChecker.check(text);
            editorPanel
                    .getMeaningfulnessPanel()
                    .showMeaningfulness(
                            meaningfulnessResult.meaningful(),
                            meaningfulnessResult.score(),
                            meaningfulnessResult.explanation(),
                            meaningfulnessResult.tokenRows());

            addToHistory(text, isPalindrome, meaningfulnessResult);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error during palindrome and meaningfulness check", e);
            resultPanel.showNotChecked();
            resultPanel.showError("");
            editorPanel.clearNormalizedText();
            editorPanel.getMeaningfulnessPanel().clear();
        }
    }

    private boolean isValidInput(String text) {
        if (text.isEmpty()) {
            resultPanel.showNotChecked();
            resultPanel.showError(bundle.getString("error.inputEmpty"));
            editorPanel.clearNormalizedText();
            return false;
        }

        if (text.length() > MAX_INPUT_LENGTH) {
            LOGGER.warning("Check skipped: input is too large, length = " + text.length());
            resultPanel.showError(bundle.getString("error.inputTooLarge"));
            editorPanel.clearNormalizedText();
            return false;
        }

        return true;
    }

    private String prepareTextForCheck(String text) {
        NormalizationSettings settings = editorPanel.getNormalizationSettings();

        if (!settings.hasAnyEnabledOption()) {
            editorPanel.clearNormalizedText();
            return text;
        }

        String normalizedText = textNormalizer.normalize(text, settings);
        editorPanel.showNormalizedText(normalizedText);
        return normalizedText;
    }

    private void showCheckResult(boolean isPalindrome) {
        if (isPalindrome) {
            resultPanel.showSuccess();
        } else {
            resultPanel.showFailure();
        }
    }

    private void addToHistory(
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

        editorPanel
                .getHistoryPanel()
                .addHistoryEntry(
                        entry.timestamp(),
                        entry.inputPreview(),
                        entry.palindromeResult(),
                        entry.meaningfulnessResult(),
                        entry.scoreText());
    }

    private String buildInputPreview(String text) {
        String singleLine = text.replaceAll("\\s+", " ").trim();

        if (singleLine.length() <= HISTORY_PREVIEW_LENGTH) {
            return singleLine;
        }

        return singleLine.substring(0, HISTORY_PREVIEW_LENGTH) + "...";
    }

    private void onExportCsvClicked() {
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

    public List<HistoryEntry> getHistoryEntries() {
        return List.copyOf(historyEntries);
    }

    private void onClearClicked() {
        LOGGER.info("Clear button clicked");
        resultPanel.showError("");
        resultPanel.showNotChecked();
        editorPanel.clearText();
        editorPanel.clearNormalizationCheckboxes();
        editorPanel.clearNormalizedText();
        editorPanel.getMeaningfulnessPanel().clear();
    }
}
