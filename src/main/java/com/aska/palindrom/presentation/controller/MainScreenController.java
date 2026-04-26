package com.aska.palindrom.presentation.controller;

import static com.aska.palindrom.presentation.logging.AppLogger.LOGGER;

import com.aska.palindrom.domain.PalindromeChecker;
import com.aska.palindrom.domain.TextNormalizer;
import com.aska.palindrom.domain.meaningfulness.combined.MeaningfulnessChecker;
import com.aska.palindrom.domain.meaningfulness.combined.MeaningfulnessResult;
import com.aska.palindrom.domain.settings.NormalizationSettings;
import com.aska.palindrom.presentation.controller.history.HistoryManager;
import com.aska.palindrom.presentation.panel.EditorPanel;
import com.aska.palindrom.presentation.panel.ResultPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class MainScreenController {
    private static final int MAX_INPUT_LENGTH = 1000000;

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    private final EditorPanel editorPanel;
    private final ResultPanel resultPanel;
    private final PalindromeChecker palindromeChecker;
    private final MeaningfulnessChecker meaningfulnessChecker;
    private final TextNormalizer textNormalizer = new TextNormalizer();
    private final HistoryManager historyManager;

    public MainScreenController(
            EditorPanel editorPanel,
            ResultPanel resultPanel,
            PalindromeChecker palindromeChecker,
            MeaningfulnessChecker meaningfulnessChecker) {
        this.editorPanel = editorPanel;
        this.resultPanel = resultPanel;
        this.palindromeChecker = palindromeChecker;
        this.meaningfulnessChecker = meaningfulnessChecker;
        this.historyManager = new HistoryManager(editorPanel, resultPanel, bundle);

        bindActions();
        historyManager.loadHistory();
    }

    private void bindActions() {
        editorPanel.getCheckButton().addActionListener(e -> onCheckClicked());
        editorPanel.getClearButton().addActionListener(e -> onClearClicked());

        editorPanel
                .getHistoryPanel()
                .getExportCsvButton()
                .addActionListener(e -> historyManager.exportCsv());

        editorPanel
                .getHistoryPanel()
                .getExportJsonButton()
                .addActionListener(e -> historyManager.exportJson());

        editorPanel
                .getHistoryPanel()
                .getClearHistoryButton()
                .addActionListener(e -> historyManager.clearHistory());

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

            historyManager.addEntry(text, isPalindrome, meaningfulnessResult);
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
