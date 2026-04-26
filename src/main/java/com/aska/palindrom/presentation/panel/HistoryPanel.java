package com.aska.palindrom.presentation.panel;

import com.aska.palindrom.presentation.config.UiBorders;
import com.aska.palindrom.presentation.config.UiColors;
import com.aska.palindrom.presentation.config.UiDimensions;
import java.awt.*;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HistoryPanel extends JPanel {
    private static final int MAX_HISTORY_ROWS = 20;

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    private final JButton exportCsvButton;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public HistoryPanel() {
        super(new BorderLayout(0, 0));
        setBorder(BorderFactory.createLineBorder(UiColors.PANEL_BORDER));

        exportCsvButton = new JButton(bundle.getString("history.button.exportCsv"));
        exportCsvButton.setPreferredSize(UiDimensions.ACTION_BUTTON_SIZE);

        tableModel =
                new DefaultTableModel(
                        new Object[] {
                            bundle.getString("history.column.time"),
                            bundle.getString("history.column.input"),
                            bundle.getString("history.column.palindrome"),
                            bundle.getString("history.column.meaningfulness"),
                            bundle.getString("history.column.score")
                        },
                        0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        table = new JTable(tableModel);

        add(createTablePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.PAGE_END);
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(UiBorders.padding(UiDimensions.TEXT_PADDING));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(UiBorders.padding(UiDimensions.TEXT_PADDING));
        panel.add(exportCsvButton);
        return panel;
    }

    public JButton getExportCsvButton() {
        return exportCsvButton;
    }

    public void addHistoryEntry(
            String timestamp,
            String inputPreview,
            String palindromeResult,
            String meaningfulnessResult,
            String scoreText) {

        if (tableModel.getRowCount() >= MAX_HISTORY_ROWS) {
            tableModel.removeRow(0);
        }

        tableModel.addRow(
                new Object[] {
                    timestamp, inputPreview, palindromeResult, meaningfulnessResult, scoreText
                });
    }

    public void clear() {
        tableModel.setRowCount(0);
    }
}
