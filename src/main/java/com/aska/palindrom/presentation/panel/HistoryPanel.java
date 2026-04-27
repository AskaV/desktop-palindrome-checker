package com.aska.palindrom.presentation.panel;

import com.aska.palindrom.domain.history.HistoryEntry;
import com.aska.palindrom.presentation.config.UiBorders;
import com.aska.palindrom.presentation.config.UiColors;
import com.aska.palindrom.presentation.config.UiDimensions;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HistoryPanel extends JPanel {
    private static final int MAX_HISTORY_ROWS = 20;

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    private final JButton exportCsvButton;
    private final JButton exportJsonButton;
    private final JButton clearHistoryButton;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final List<HistoryEntry> visibleEntries = new ArrayList<>();

    public HistoryPanel() {
        super(new BorderLayout(0, 0));
        setBorder(BorderFactory.createLineBorder(UiColors.PANEL_BORDER));

        exportCsvButton = new JButton(bundle.getString("history.button.exportCsv"));
        exportCsvButton.setPreferredSize(UiDimensions.ACTION_BUTTON_SIZE);
        exportJsonButton = new JButton(bundle.getString("history.button.exportJson"));
        exportJsonButton.setPreferredSize(UiDimensions.ACTION_BUTTON_SIZE);
        clearHistoryButton = new JButton(bundle.getString("history.button.clear"));
        clearHistoryButton.setPreferredSize(UiDimensions.ACTION_BUTTON_SIZE);

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
        installDoubleClickHandler();

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
        panel.add(exportJsonButton);
        panel.add(clearHistoryButton);
        return panel;
    }

    private void installDoubleClickHandler() {
        table.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() != 2) {
                            return;
                        }

                        int row = table.rowAtPoint(e.getPoint());
                        if (row < 0 || row >= visibleEntries.size()) {
                            return;
                        }

                        showFullInputDialog(visibleEntries.get(row));
                    }
                });
    }

    private void showFullInputDialog(HistoryEntry entry) {
        JDialog dialog = new JDialog((Dialog) null, bundle.getString("history.dialog.title"), true);
        dialog.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(entry.fullInput());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);
        textArea.setBorder(UiBorders.padding(UiDimensions.TEXT_PADDING));

        dialog.add(new JScrollPane(textArea), BorderLayout.CENTER);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void addHistoryEntry(HistoryEntry entry, String inputPreview) {
        if (tableModel.getRowCount() >= MAX_HISTORY_ROWS) {
            tableModel.removeRow(0);
            visibleEntries.remove(0);
        }

        visibleEntries.add(entry);

        tableModel.addRow(
                new Object[] {
                    entry.timestamp(),
                    inputPreview,
                    entry.palindromeResult(),
                    entry.meaningfulnessResult(),
                    entry.scoreText()
                });
    }

    public JButton getExportCsvButton() {
        return exportCsvButton;
    }

    public JButton getExportJsonButton() {
        return exportJsonButton;
    }

    public JButton getClearHistoryButton() {
        return clearHistoryButton;
    }

    public void clear() {
        tableModel.setRowCount(0);
        visibleEntries.clear();
    }
}
