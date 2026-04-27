package com.aska.palindrom.presentation.panel;

import com.aska.palindrom.domain.settings.NormalizationSettings;
import com.aska.palindrom.presentation.config.UiBorders;
import com.aska.palindrom.presentation.config.UiColors;
import com.aska.palindrom.presentation.config.UiDimensions;
import com.aska.palindrom.presentation.config.UiFonts;
import com.aska.palindrom.presentation.panel.components.NormalizationOptionsButton;
import com.aska.palindrom.presentation.panel.components.TextFileDropHandler;
import com.aska.palindrom.presentation.panel.meaningfulness.MeaningfulnessPanel;
import java.awt.*;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javax.swing.*;

public class EditorPanel extends JPanel {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");
    private final JTextArea inputArea;
    private final JTextArea normalizedArea;
    private final MeaningfulnessPanel meaningfulnessPanel;
    private final HistoryPanel historyPanel;
    private final JButton checkButton;
    private final JButton clearButton;
    private final NormalizationOptionsButton normalizationButton;

    public EditorPanel() {
        super(new BorderLayout(0, UiDimensions.EDITOR_GAP));
        setBorder(BorderFactory.createLineBorder(UiColors.PANEL_BORDER));

        inputArea = createTextArea(true);
        normalizedArea = createTextArea(false);
        meaningfulnessPanel = new MeaningfulnessPanel();
        historyPanel = new HistoryPanel();

        checkButton = new JButton(bundle.getString("button.check"));
        clearButton = new JButton(bundle.getString("button.clear"));
        normalizationButton = new NormalizationOptionsButton(bundle);

        add(createTabsPanel(), BorderLayout.CENTER);
    }

    private JTabbedPane createTabsPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(bundle.getString("tab.input"), createInputTab());
        tabbedPane.addTab(bundle.getString("tab.normalized"), new JScrollPane(normalizedArea));
        tabbedPane.addTab(bundle.getString("tab.meaningfulness"), meaningfulnessPanel);
        tabbedPane.addTab(bundle.getString("tab.history"), historyPanel);

        return tabbedPane;
    }

    private JPanel createInputTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        panel.add(createButtonsPanel(), BorderLayout.PAGE_END);
        return panel;
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

    public void installInputFileDropHandler(Runnable onSuccess, Consumer<String> onError) {
        inputArea.setTransferHandler(new TextFileDropHandler(inputArea, onSuccess, onError));
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

    public MeaningfulnessPanel getMeaningfulnessPanel() {
        return meaningfulnessPanel;
    }

    public HistoryPanel getHistoryPanel() {
        return historyPanel;
    }
}
