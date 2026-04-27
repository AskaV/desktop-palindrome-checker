package com.aska.palindrom.presentation.panel.meaningfulness;

import com.aska.palindrom.presentation.config.UiColors;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MeaningfulnessBooleanCellRenderer extends DefaultTableCellRenderer {
    private final boolean dictionaryColumn;
    private final double positiveScore;

    public MeaningfulnessBooleanCellRenderer(boolean dictionaryColumn, double positiveScore) {
        this.dictionaryColumn = dictionaryColumn;
        this.positiveScore = positiveScore;
        setHorizontalAlignment(CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component component =
                super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

        if (value instanceof Boolean booleanValue) {
            if (dictionaryColumn) {
                setText(booleanValue ? "Yes" : "No");
            } else {
                setText(booleanValue ? positiveScore + "%" : "0%");
            }

            if (!isSelected) {
                component.setBackground(booleanValue ? UiColors.TABLE_GREEN : UiColors.TABLE_RED);
            }
        } else if (!isSelected) {
            component.setBackground(Color.WHITE);
        }

        return component;
    }
}
