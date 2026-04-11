package com.aska.palindrom.presentation.panel;

import com.aska.palindrom.presentation.config.UiBorders;
import com.aska.palindrom.presentation.config.UiColors;
import com.aska.palindrom.presentation.config.UiDimensions;
import com.aska.palindrom.presentation.config.UiFonts;
import java.awt.*;
import java.util.ResourceBundle;
import javax.swing.*;

public class EditorPanel extends JPanel {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");
    private final JTextArea inputArea;
    private final JButton checkButton;
    private final JButton clearButton;

    public EditorPanel() {
        super(new BorderLayout(0, UiDimensions.EDITOR_GAP));
        setBorder(BorderFactory.createLineBorder(UiColors.PANEL_BORDER));

        inputArea = createTextArea(true);

        checkButton = new JButton(bundle.getString("button.check"));
        clearButton = new JButton(bundle.getString("button.clear"));

        add(createTabsPanel(), BorderLayout.CENTER);
        add(createButtonsPanel(), BorderLayout.PAGE_END);
    }

    private JTabbedPane createTabsPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(bundle.getString("tab.input"), new JScrollPane(inputArea));
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

        panel.add(checkButton);
        panel.add(clearButton);
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
}
