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

    public ResultPanel() {
        super(new BorderLayout(0, UiDimensions.SMALL));

        JLabel resultLabel = new JLabel(bundle.getString("label.result-text"));
        resultLabel.setFont(UiFonts.RESULT_LABEL);

        resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setHorizontalAlignment(JTextField.CENTER);
        resultField.setFont(UiFonts.RESULT_FIELD);
        resultField.setBorder(UiBorders.padding(UiDimensions.RESULT_GAP));

        showNotChecked();

        add(resultLabel, BorderLayout.PAGE_START);
        add(resultField, BorderLayout.CENTER);
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
}
