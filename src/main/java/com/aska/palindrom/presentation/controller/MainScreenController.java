package com.aska.palindrom.presentation.controller;

import static com.aska.palindrom.presentation.logging.AppLogger.LOGGER;

import com.aska.palindrom.domain.PalindromeChecker;
import com.aska.palindrom.domain.TextNormalizer;
import com.aska.palindrom.domain.settings.NormalizationSettings;
import com.aska.palindrom.presentation.panel.EditorPanel;
import com.aska.palindrom.presentation.panel.ResultPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class MainScreenController {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");
    private final EditorPanel editorPanel;
    private final ResultPanel resultPanel;
    private final PalindromeChecker palindromeChecker;
    private final TextNormalizer textNormalizer = new TextNormalizer();
    private static final int MAX_INPUT_LENGTH = 1000000;

    public MainScreenController(
            EditorPanel editorPanel, ResultPanel resultPanel, PalindromeChecker palindromeChecker) {
        this.editorPanel = editorPanel;
        this.resultPanel = resultPanel;
        this.palindromeChecker = palindromeChecker;

        bindActions();
    }

    private void bindActions() {
        editorPanel.getCheckButton().addActionListener(e -> onCheckClicked());
        editorPanel.getClearButton().addActionListener(e -> onClearClicked());

        editorPanel
                .getInputArea()
                .addKeyListener(
                        new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                resultPanel.showNotChecked();
                                resultPanel.showError("");
                            }
                        });
    }

    private void onCheckClicked() {
        LOGGER.info("Check button clicked");

        try {
            String text = editorPanel.getInputArea().getText();

            if (!isValidInput(text)) {
                return;
            }

            String textForCheck = prepareTextForCheck(text);
            boolean isPalindrome = palindromeChecker.isPalindrome(textForCheck);

            showCheckResult(isPalindrome);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error during palindrome check", e);
            resultPanel.showNotChecked();
        }
    }

    private boolean isValidInput(String text) {
        if (text.isEmpty()) {
            resultPanel.showNotChecked();
            resultPanel.showError(bundle.getString("error.inputEmpty"));
            editorPanel.showNormalizedText("");
            return false;
        }

        if (text.length() > MAX_INPUT_LENGTH) {
            LOGGER.warning("Check skipped: input is too large, length = " + text.length());
            resultPanel.showError(bundle.getString("error.inputTooLarge"));
            editorPanel.showNormalizedText("");
            return false;
        }

        return true;
    }

    private String prepareTextForCheck(String text) {
        NormalizationSettings settings = editorPanel.getNormalizationSettings();

        if (!settings.hasAnyEnabledOption()) {
            editorPanel.showNormalizedText("");
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
        editorPanel.clearText();
        editorPanel.clearNormalizationCheckboxes();
        editorPanel.clearNormalizedText();
        resultPanel.showNotChecked();
    }
}
