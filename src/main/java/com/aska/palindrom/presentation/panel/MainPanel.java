package com.aska.palindrom.presentation.panel;

import com.aska.palindrom.domain.PalindromeChecker;
import com.aska.palindrom.domain.meaningfulness.combined.MeaningfulnessChecker;
import com.aska.palindrom.presentation.config.UiDimensions;
import com.aska.palindrom.presentation.controller.MainScreenController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainPanel extends JPanel {
    public MainPanel() {
        super(new BorderLayout(0, UiDimensions.MAIN_GAP));
        setBorder(new EmptyBorder(UiDimensions.MAIN_PADDING));

        EditorPanel editorPanel = new EditorPanel();
        ResultPanel resultPanel = new ResultPanel();

        new MainScreenController(
                editorPanel, resultPanel, new PalindromeChecker(), new MeaningfulnessChecker());

        add(editorPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.PAGE_END);
    }
}
