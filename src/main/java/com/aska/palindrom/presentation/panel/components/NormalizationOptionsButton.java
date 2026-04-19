package com.aska.palindrom.presentation.panel.components;

import com.aska.palindrom.domain.settings.NormalizationSettings;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class NormalizationOptionsButton extends JButton {
    private static final NormalizationSettings INITIAL_SETTINGS =
            new NormalizationSettings(false, false, false, false, false);
    private static final String DROPDOWN_SYMBOL = "\u25BE ";
    private final ResourceBundle bundle;
    private final JPopupMenu popupMenu;

    private final JCheckBox ignoreCaseCheckBox;
    private final JCheckBox ignoreSpacesCheckBox;
    private final JCheckBox ignorePunctuationCheckBox;
    private final JCheckBox unicodeNormalizationCheckBox;
    private final JCheckBox ignoreDiacriticsCheckBox;

    public NormalizationOptionsButton(ResourceBundle bundle) {
        super(DROPDOWN_SYMBOL + bundle.getString("button.normalization"));

        this.bundle = bundle;
        this.popupMenu = new JPopupMenu();

        ignoreCaseCheckBox =
                createCheckBox("normalization.ignoreCase", INITIAL_SETTINGS.ignoreCase());
        ignoreSpacesCheckBox =
                createCheckBox("normalization.ignoreSpaces", INITIAL_SETTINGS.ignoreSpaces());
        ignorePunctuationCheckBox =
                createCheckBox(
                        "normalization.ignorePunctuation", INITIAL_SETTINGS.ignorePunctuation());
        unicodeNormalizationCheckBox =
                createCheckBox("normalization.unicode", INITIAL_SETTINGS.unicodeNormalization());
        ignoreDiacriticsCheckBox =
                createCheckBox(
                        "normalization.ignoreDiacritics", INITIAL_SETTINGS.ignoreDiacritics());

        configurePopupMenu();
        configurePopupOpening();
    }

    private JCheckBox createCheckBox(String textKey, boolean selected) {
        JCheckBox checkBox = new JCheckBox(bundle.getString(textKey), selected);
        checkBox.setFocusable(false);
        return checkBox;
    }

    private void configurePopupMenu() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        optionsPanel.add(ignoreCaseCheckBox);
        optionsPanel.add(ignoreSpacesCheckBox);
        optionsPanel.add(ignorePunctuationCheckBox);
        optionsPanel.add(unicodeNormalizationCheckBox);
        optionsPanel.add(ignoreDiacriticsCheckBox);

        popupMenu.add(optionsPanel);
    }

    private void configurePopupOpening() {
        addActionListener(event -> popupMenu.show(this, 0, getHeight()));
    }

    public NormalizationSettings getNormalizationSettings() {
        return new NormalizationSettings(
                ignoreCaseCheckBox.isSelected(),
                ignoreSpacesCheckBox.isSelected(),
                ignorePunctuationCheckBox.isSelected(),
                unicodeNormalizationCheckBox.isSelected(),
                ignoreDiacriticsCheckBox.isSelected());
    }

    public void resetSettings() {
        setSettings(INITIAL_SETTINGS);
    }

    private void setSettings(NormalizationSettings settings) {
        ignoreCaseCheckBox.setSelected(settings.ignoreCase());
        ignoreSpacesCheckBox.setSelected(settings.ignoreSpaces());
        ignorePunctuationCheckBox.setSelected(settings.ignorePunctuation());
        unicodeNormalizationCheckBox.setSelected(settings.unicodeNormalization());
        ignoreDiacriticsCheckBox.setSelected(settings.ignoreDiacritics());
    }
}
