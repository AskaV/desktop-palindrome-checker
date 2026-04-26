package com.aska.palindrom.presentation;

import com.aska.palindrom.domain.meaningfulness.TokenAnalysisRow;
import com.aska.palindrom.presentation.config.UiBorders;
import com.aska.palindrom.presentation.config.UiColors;
import com.aska.palindrom.presentation.config.UiDimensions;
import com.aska.palindrom.presentation.config.UiFonts;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MeaningfulnessPanel extends JPanel {
    private static final int MAIN_COLUMN_COUNT = 9;
    private static final int GROUP_WORD_COLUMNS = 1;
    private static final int GROUP_DICTIONARY_COLUMNS = 2;
    private static final int GROUP_HEURISTIC_COLUMNS = 6;

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    private final JLabel resultLabel;
    private final JLabel scoreLabel;
    private final JLabel explanationLabel;

    private final JTable groupTable;
    private final DefaultTableModel groupTableModel;
    private final JScrollPane groupScrollPane;

    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JScrollPane tableScrollPane;

    private final JPanel tableSectionPanel;

    public MeaningfulnessPanel() {
        super(new BorderLayout(0, 0));
        setBorder(BorderFactory.createLineBorder(UiColors.PANEL_BORDER));

        resultLabel = new JLabel(bundle.getString("meaningfulness.result"));
        resultLabel.setFont(UiFonts.RESULT_LABEL);

        scoreLabel = new JLabel(bundle.getString("meaningfulness.score"));
        scoreLabel.setFont(UiFonts.TEXT_AREA);

        explanationLabel = new JLabel(bundle.getString("meaningfulness.explanation"));
        explanationLabel.setFont(UiFonts.TEXT_AREA);

        groupTableModel =
                new DefaultTableModel(
                        new Object[][] {
                            {
                                bundle.getString("meaningfulness.group.word"),
                                bundle.getString("meaningfulness.group.dictionary"),
                                bundle.getString("meaningfulness.group.heuristic")
                            }
                        },
                        new Object[] {"", "", ""}) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        groupTable = new JTable(groupTableModel);
        groupScrollPane = new JScrollPane(groupTable);

        tableModel =
                new DefaultTableModel(
                        new Object[] {
                            bundle.getString("meaningfulness.column.token"),
                            bundle.getString("meaningfulness.column.dictionary"),
                            bundle.getString("meaningfulness.column.dictionaryScore"),
                            bundle.getString("meaningfulness.column.vowel"),
                            bundle.getString("meaningfulness.column.consonants"),
                            bundle.getString("meaningfulness.column.repeats"),
                            bundle.getString("meaningfulness.column.rarePattern"),
                            bundle.getString("meaningfulness.column.length"),
                            bundle.getString("meaningfulness.column.heuristicScore")
                        },
                        0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        table = new JTable(tableModel);
        tableScrollPane = new JScrollPane(table);

        configureGroupTable();
        configureTable();

        tableSectionPanel = createTableSection();

        add(createHeaderPanel(), BorderLayout.PAGE_START);
        add(tableSectionPanel, BorderLayout.CENTER);

        installResizeHandling();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(UiBorders.padding(UiDimensions.TEXT_PADDING));

        resultLabel.setAlignmentX(LEFT_ALIGNMENT);
        scoreLabel.setAlignmentX(LEFT_ALIGNMENT);
        explanationLabel.setAlignmentX(LEFT_ALIGNMENT);

        panel.add(resultLabel);
        panel.add(scoreLabel);
        panel.add(explanationLabel);

        return panel;
    }

    private JPanel createTableSection() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBorder(UiBorders.padding(UiDimensions.TEXT_PADDING));

        groupScrollPane.setPreferredSize(new Dimension(0, groupTable.getRowHeight() + 4));
        panel.add(groupScrollPane, BorderLayout.PAGE_START);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void configureGroupTable() {
        groupTable.setRowHeight(28);
        groupTable.setEnabled(false);
        groupTable.setCellSelectionEnabled(false);
        groupTable.setRowSelectionAllowed(false);
        groupTable.setColumnSelectionAllowed(false);
        groupTable.setTableHeader(null);
        groupTable.setShowGrid(true);
        groupTable.setFocusable(false);
        groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        groupScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        groupScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void configureTable() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DefaultTableCellRenderer booleanRenderer =
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table, value, isSelected, hasFocus, row, column);

                        if (value instanceof Boolean booleanValue) {
                            setText(booleanValue ? "Yes" : "No");

                            if (!isSelected) {
                                component.setBackground(
                                        booleanValue ? UiColors.TABLE_GREEN : UiColors.TABLE_RED);
                            }
                        } else {
                            if (!isSelected) {
                                component.setBackground(Color.WHITE);
                            }
                        }

                        return component;
                    }
                };

        int[] booleanColumns = {1, 3, 4, 5, 6, 7};
        for (int columnIndex : booleanColumns) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(booleanRenderer);
        }
    }

    private void installResizeHandling() {
        tableSectionPanel.addComponentListener(
                new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        updateTableWidths();
                    }
                });
    }

    private void updateTableWidths() {
        int tableWidth = tableScrollPane.getViewport().getWidth();

        if (tableWidth <= 0) {
            return;
        }

        int mainColumnWidth = tableWidth / MAIN_COLUMN_COUNT;
        int remainder = tableWidth - (mainColumnWidth * MAIN_COLUMN_COUNT);

        for (int i = 0; i < MAIN_COLUMN_COUNT; i++) {
            int width = mainColumnWidth;
            if (i == MAIN_COLUMN_COUNT - 1) {
                width += remainder;
            }

            table.getColumnModel().getColumn(i).setPreferredWidth(width);
            table.getColumnModel().getColumn(i).setWidth(width);
        }

        int groupWordWidth = mainColumnWidth * GROUP_WORD_COLUMNS;
        int groupDictionaryWidth = mainColumnWidth * GROUP_DICTIONARY_COLUMNS;
        int groupHeuristicWidth = (mainColumnWidth * GROUP_HEURISTIC_COLUMNS) + remainder;

        groupTable.getColumnModel().getColumn(0).setPreferredWidth(groupWordWidth);
        groupTable.getColumnModel().getColumn(0).setWidth(groupWordWidth);

        groupTable.getColumnModel().getColumn(1).setPreferredWidth(groupDictionaryWidth);
        groupTable.getColumnModel().getColumn(1).setWidth(groupDictionaryWidth);

        groupTable.getColumnModel().getColumn(2).setPreferredWidth(groupHeuristicWidth);
        groupTable.getColumnModel().getColumn(2).setWidth(groupHeuristicWidth);

        groupTable.revalidate();
        table.revalidate();
    }

    public void showMeaningfulness(
            boolean meaningful,
            double score,
            String explanation,
            List<TokenAnalysisRow> tokenRows) {

        resultLabel.setText(
                bundle.getString("meaningfulness.result")
                        + " "
                        + (meaningful
                                ? bundle.getString("meaningfulness.meaningful")
                                : bundle.getString("meaningfulness.notMeaningful")));

        scoreLabel.setText(bundle.getString("meaningfulness.score") + " " + score + "%");
        explanationLabel.setText(
                bundle.getString("meaningfulness.explanation") + " " + explanation);

        tableModel.setRowCount(0);

        for (TokenAnalysisRow row : tokenRows) {
            tableModel.addRow(
                    new Object[] {
                        row.token(),
                        row.foundInDictionary(),
                        row.dictionaryScore(),
                        row.hasVowel(),
                        row.noLongConsonantSequence(),
                        row.noRepeatedCharacterSequence(),
                        row.noRarePattern(),
                        row.reasonableLength(),
                        row.heuristicScore()
                    });
        }

        updateTableWidths();
    }

    public void clear() {
        resultLabel.setText(bundle.getString("meaningfulness.result"));
        scoreLabel.setText(bundle.getString("meaningfulness.score"));
        explanationLabel.setText(bundle.getString("meaningfulness.explanation"));
        tableModel.setRowCount(0);
        updateTableWidths();
    }
}
