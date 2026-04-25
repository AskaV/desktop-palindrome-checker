package com.aska.palindrom.presentation;

import com.aska.palindrom.presentation.config.UiColors;
import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HistoryPanel extends JPanel {
    private final DefaultTableModel tableModel;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    public HistoryPanel() {
        super(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(UiColors.PANEL_BORDER));

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

        JTable table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void addHistoryEntry(
            String time,
            String inputPreview,
            String palindromeResult,
            String meaningfulnessResult,
            String score) {

        if (tableModel.getRowCount() >= 20) {
            tableModel.removeRow(0);
        }

        tableModel.addRow(
                new Object[] {time, inputPreview, palindromeResult, meaningfulnessResult, score});
    }

    public void clear() {
        tableModel.setRowCount(0);
    }
}
