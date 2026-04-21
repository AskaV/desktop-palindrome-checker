package com.aska.palindrom.presentation.panel;

import com.aska.palindrom.domain.settings.NormalizationSettings;
import com.aska.palindrom.presentation.config.UiBorders;
import com.aska.palindrom.presentation.config.UiColors;
import com.aska.palindrom.presentation.config.UiDimensions;
import com.aska.palindrom.presentation.config.UiFonts;
import com.aska.palindrom.presentation.panel.components.NormalizationOptionsButton;
import java.awt.*;
import java.util.ResourceBundle;
import javax.swing.*;

public class EditorPanel extends JPanel {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");
    private final JTextArea inputArea;
    private final JTextArea normalizedArea;
    private final JButton checkButton;
    private final JButton clearButton;
    private final NormalizationOptionsButton normalizationButton;

    public EditorPanel() {
        super(new BorderLayout(0, UiDimensions.EDITOR_GAP));
        setBorder(BorderFactory.createLineBorder(UiColors.PANEL_BORDER));

        inputArea = createTextArea(true);
        normalizedArea = createTextArea(false);

        checkButton = new JButton(bundle.getString("button.check"));
        clearButton = new JButton(bundle.getString("button.clear"));
        normalizationButton = new NormalizationOptionsButton(bundle);

        add(createTabsPanel(), BorderLayout.CENTER);
        add(createButtonsPanel(), BorderLayout.PAGE_END);
    }

    private JTabbedPane createTabsPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(bundle.getString("tab.input"), new JScrollPane(inputArea));
        tabbedPane.addTab(bundle.getString("tab.normalized"), new JScrollPane(normalizedArea));

        return tabbedPane;
    }

    private JPanel createButtonsPanel() {
        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                UiDimensions.BUTTONS_GAP,
                                UiDimensions.BUTTONS_GAP));

        checkButton.setPreferredSize(UiDimensions.ACTION_BUTTON_SIZE);
        clearButton.setPreferredSize(UiDimensions.ACTION_BUTTON_SIZE);
        normalizationButton.setPreferredSize(UiDimensions.ACTION_BUTTON_SIZE);

        panel.add(checkButton);
        panel.add(clearButton);
        panel.add(normalizationButton);

        return panel;
    }

    private JTextArea createTextArea(boolean editable) {
        JTextArea area = new JTextArea();
        area.setFont(UiFonts.TEXT_AREA);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(editable);
        area.setBorder(UiBorders.padding(UiDimensions.TEXT_PADDING));

        return area;
    }

    public JTextArea getInputArea() {
        return inputArea;
    }

    public JButton getCheckButton() {
        return checkButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public void clearText() {
        inputArea.setText("");
    }

    public void clearNormalizationCheckboxes() {
        normalizationButton.resetSettings();
    }

    public NormalizationSettings getNormalizationSettings() {
        return normalizationButton.getNormalizationSettings();
    }

    public void showNormalizedText(String text) {
        normalizedArea.setText(text);
        normalizedArea.setCaretPosition(0);
    }

    public void clearNormalizedText() {
        normalizedArea.setText("");
    }
}
