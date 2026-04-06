package com.aska.palindrom.presentation;

import com.aska.palindrom.presentation.config.UiColors;
import com.aska.palindrom.presentation.config.UiDimensions;
import com.aska.palindrom.presentation.config.WindowSettings;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");
    private final WindowSettings windowSettings = WindowSettings.defaultSettings();
    private JTextArea inputArea;
    private JTextArea normalizedArea;
    private JTextField resultField;

    public MainFrame() {
        configureWindow(windowSettings);

        setContentPane(createMainPanel());

        setVisible(true);
    }

    private void configureWindow(WindowSettings settings) {
        setTitle(settings.appTitle());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(settings.minWindowWidth(), settings.minWindowHeight()));
        setSize(settings.windowWidth(), settings.windowHeight());

        if (settings.centerOnScreen()) {
            setLocationRelativeTo(null);
        }

        URL iconUrl = MainFrame.class.getResource(settings.iconPath());
        if (iconUrl != null) {
            setIconImage(new ImageIcon(iconUrl).getImage());
        }
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, UiDimensions.MAIN_GAP));
        mainPanel.setBorder(new EmptyBorder(UiDimensions.MAIN_PADDING));

        mainPanel.add(createEditorBlock(), BorderLayout.CENTER);
        mainPanel.add(createResultBlock(), BorderLayout.PAGE_END);

        return mainPanel;
    }

    private JPanel createEditorBlock() {
        JPanel editorBlock = new JPanel(new BorderLayout(0, UiDimensions.EDITOR_GAP));
        editorBlock.setBorder(BorderFactory.createLineBorder(UiColors.PANEL_BORDER));

        editorBlock.add(createTabsPanel(), BorderLayout.CENTER);
        editorBlock.add(createButtonsPanel(), BorderLayout.PAGE_END);

        return editorBlock;
    }

    private JTabbedPane createTabsPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();

        inputArea = createTextArea(true);
        normalizedArea = createTextArea(false);

        JScrollPane inputScroll = new JScrollPane(inputArea);
        JScrollPane normalizedScroll = new JScrollPane(normalizedArea);

        tabbedPane.addTab(bundle.getString("tab.input"), inputScroll);
        tabbedPane.addTab(bundle.getString("tab.normalized"), normalizedScroll);

        return tabbedPane;
    }

    private JTextArea createTextArea(boolean editable) {
        JTextArea area = new JTextArea();
        area.setFont(new Font("Dialog", Font.PLAIN, 20));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(editable);
        area.setBorder(paddingBorder(UiDimensions.TEXT_PADDING));

        return area;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                UiDimensions.BUTTONS_GAP,
                                UiDimensions.BUTTONS_GAP));

        JButton checkButton = new JButton(bundle.getString("button.check"));
        JButton normalizeButton = new JButton(bundle.getString("button.normalize"));

        checkButton.setPreferredSize(UiDimensions.ACTION_BUTTON_SIZE);
        normalizeButton.setPreferredSize(UiDimensions.ACTION_BUTTON_SIZE);

        buttonsPanel.add(checkButton);
        buttonsPanel.add(normalizeButton);

        return buttonsPanel;
    }

    private JPanel createResultBlock() {
        JPanel resultBlock = new JPanel(new BorderLayout(0, UiDimensions.SMALL));

        JLabel resultLabel = new JLabel(bundle.getString("label.result"));
        resultLabel.setFont(new Font("Dialog", Font.BOLD, 18));

        resultField = new JTextField(bundle.getString("label.not_checked"));
        resultField.setEditable(false);
        resultField.setHorizontalAlignment(JTextField.CENTER);
        resultField.setFont(new Font("Dialog", Font.PLAIN, 18));
        resultField.setBackground(UiColors.RESULT_NEUTRAL);
        resultField.setBorder(paddingBorder(UiDimensions.RESULT_GAP));

        resultBlock.add(resultLabel, BorderLayout.PAGE_START);
        resultBlock.add(resultField, BorderLayout.CENTER);

        return resultBlock;
    }

    private EmptyBorder paddingBorder(int padding) {
        return new EmptyBorder(padding, padding, padding, padding);
    }
}
