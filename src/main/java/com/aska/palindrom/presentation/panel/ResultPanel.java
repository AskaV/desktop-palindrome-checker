package com.aska.palindrom.presentation.panel;

import com.aska.palindrom.presentation.config.UiBorders;
import com.aska.palindrom.presentation.config.UiColors;
import com.aska.palindrom.presentation.config.UiDimensions;
import com.aska.palindrom.presentation.config.UiFonts;
import java.awt.*;
import java.util.ResourceBundle;
import javax.swing.*;

public class ResultPanel extends JPanel {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");
    private final JTextField resultField;
    private final JLabel errorLabel;

    public ResultPanel() {
        super(new BorderLayout(0, UiDimensions.SMALL));

        JLabel resultLabel = new JLabel(bundle.getString("label.result-text"));
        resultLabel.setFont(UiFonts.RESULT_LABEL);

        resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setHorizontalAlignment(JTextField.CENTER);
        resultField.setFont(UiFonts.RESULT_FIELD);
        resultField.setBorder(UiBorders.padding(UiDimensions.RESULT_GAP));

        errorLabel = new JLabel(" ");
        errorLabel.setFont(UiFonts.ERROR_LABEL);
        errorLabel.setForeground(UiColors.RESULT_ERROR);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Dimension errorSize = new Dimension(0, 18);
        errorLabel.setPreferredSize(errorSize);
        errorLabel.setMinimumSize(errorSize);

        showNotChecked();

        add(resultLabel, BorderLayout.PAGE_START);
        add(resultField, BorderLayout.CENTER);
        add(errorLabel, BorderLayout.PAGE_END);
    }

    public void showNotChecked() {
        resultField.setText(bundle.getString("label.not_checked"));
        resultField.setBackground(UiColors.RESULT_NEUTRAL);
    }

    public void showSuccess() {
        resultField.setText(bundle.getString("label.result.yes"));
        resultField.setBackground(UiColors.RESULT_SUCCESS);
    }

    public void showFailure() {
        resultField.setText(bundle.getString("label.result.no"));
        resultField.setBackground(UiColors.RESULT_ERROR);
    }

    public void showError(String errorText) {
        errorLabel.setForeground(UiColors.RESULT_ERROR);
        errorLabel.setText(errorText);
    }

    public void showSuccessMessage(String message) {
        errorLabel.setForeground(UiColors.RESULT_SUCCESS);
        errorLabel.setText(message);
    }
}
