package com.aska.palindrom.presentation.panel.meaningfulness;

import com.aska.palindrom.domain.meaningfulness.TokenAnalysisRow;
import com.aska.palindrom.presentation.config.UiBorders;
import com.aska.palindrom.presentation.config.UiDimensions;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MeaningfulnessAnalysisTablePanel extends JPanel {
    private static final int MAIN_COLUMN_COUNT = 9;
    private static final int GROUP_WORD_COLUMNS = 1;
    private static final int GROUP_DICTIONARY_COLUMNS = 2;
    private static final int GROUP_HEURISTIC_COLUMNS = 6;
    private static final double DICTIONARY_POSITIVE_SCORE = 100.0;
    private static final int HEURISTIC_CHECK_COUNT = 5;
    private static final double HEURISTIC_POSITIVE_SCORE = 100.0 / HEURISTIC_CHECK_COUNT;

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    private final JTable groupTable;
    private final DefaultTableModel groupTableModel;
    private final JScrollPane groupScrollPane;

    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JScrollPane tableScrollPane;

    public MeaningfulnessAnalysisTablePanel() {
        super(new BorderLayout(0, 0));
        setBorder(UiBorders.padding(UiDimensions.TEXT_PADDING));

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

        add(createTableSection(), BorderLayout.CENTER);
        installResizeHandling();
    }

    private JPanel createTableSection() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));

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

        DefaultTableCellRenderer groupRenderer = new DefaultTableCellRenderer();
        groupRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        for (int i = 0; i < groupTable.getColumnModel().getColumnCount(); i++) {
            groupTable.getColumnModel().getColumn(i).setCellRenderer(groupRenderer);
        }

        groupScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        groupScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void configureTable() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        MeaningfulnessBooleanCellRenderer dictionaryRenderer =
                new MeaningfulnessBooleanCellRenderer(true, DICTIONARY_POSITIVE_SCORE);

        MeaningfulnessBooleanCellRenderer heuristicRenderer =
                new MeaningfulnessBooleanCellRenderer(false, HEURISTIC_POSITIVE_SCORE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        table.getColumnModel().getColumn(1).setCellRenderer(dictionaryRenderer);

        int[] heuristicColumns = {3, 4, 5, 6, 7};
        for (int columnIndex : heuristicColumns) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(heuristicRenderer);
        }

        int[] centeredColumns = {0, 2, 8};
        for (int columnIndex : centeredColumns) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
        }
    }

    private void installResizeHandling() {
        addComponentListener(
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

    public void showRows(List<TokenAnalysisRow> tokenRows) {
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
        tableModel.setRowCount(0);
        updateTableWidths();
    }
}
