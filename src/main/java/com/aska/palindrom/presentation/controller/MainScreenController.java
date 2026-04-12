package com.aska.palindrom.presentation.controller;

import static com.aska.palindrom.presentation.logging.AppLogger.LOGGER;

import com.aska.palindrom.domain.PalindromeChecker;
import com.aska.palindrom.presentation.panel.EditorPanel;
import com.aska.palindrom.presentation.panel.ResultPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

public class MainScreenController {
    private final EditorPanel editorPanel;
    private final ResultPanel resultPanel;
    private final PalindromeChecker palindromeChecker;

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
            if (text.isEmpty()) {
                resultPanel.showNotChecked();
                resultPanel.showError("Input is empty");
                return;
            }
            if (text.length() > MAX_INPUT_LENGTH) {
                LOGGER.warning("Check skipped: input is too large, length = " + text.length());
                resultPanel.showError("Input is too large");
                return;
            }

            boolean isPalindrome = palindromeChecker.isPalindrome(text);

            if (isPalindrome) {
                resultPanel.showSuccess();
            } else {
                resultPanel.showFailure();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error during palindrome check", e);
            resultPanel.showNotChecked();
        }
    }

    private void onClearClicked() {
        LOGGER.info("Clear button clicked");

        editorPanel.clearText();
        resultPanel.showNotChecked();
    }
}
