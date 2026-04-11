package com.aska.palindrom.presentation.controller;

import com.aska.palindrom.domain.PalindromeChecker;
import com.aska.palindrom.presentation.panel.EditorPanel;
import com.aska.palindrom.presentation.panel.ResultPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainScreenController {
    private final EditorPanel editorPanel;
    private final ResultPanel resultPanel;
    private final PalindromeChecker palindromeChecker;

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
                            }
                        });
    }

    private void onCheckClicked() {
        String text = editorPanel.getInputArea().getText();

        boolean isPalindrome = palindromeChecker.isPalindrome(text);

        if (text.isEmpty()) {
            resultPanel.showNotChecked();
        } else if (isPalindrome) {
            resultPanel.showSuccess();
        } else {
            resultPanel.showFailure();
        }
    }

    private void onClearClicked() {
        editorPanel.clearText();
        resultPanel.showNotChecked();
    }
}
