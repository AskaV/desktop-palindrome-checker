package com.aska.palindrom.presentation.panel.meaningfulness;

import com.aska.palindrom.domain.meaningfulness.TokenAnalysisRow;
import com.aska.palindrom.presentation.config.UiBorders;
import com.aska.palindrom.presentation.config.UiColors;
import com.aska.palindrom.presentation.config.UiDimensions;
import com.aska.palindrom.presentation.config.UiFonts;
import java.awt.BorderLayout;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MeaningfulnessPanel extends JPanel {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    private final JLabel resultLabel;
    private final JLabel scoreLabel;
    private final JLabel explanationLabel;
    private final MeaningfulnessAnalysisTablePanel analysisTablePanel;

    public MeaningfulnessPanel() {
        super(new BorderLayout(0, 0));
        setBorder(BorderFactory.createLineBorder(UiColors.PANEL_BORDER));

        resultLabel = new JLabel(bundle.getString("meaningfulness.result"));
        resultLabel.setFont(UiFonts.RESULT_LABEL);

        scoreLabel = new JLabel(bundle.getString("meaningfulness.score"));
        scoreLabel.setFont(UiFonts.TEXT_AREA);

        explanationLabel = new JLabel(bundle.getString("meaningfulness.explanation"));
        explanationLabel.setFont(UiFonts.TEXT_AREA);

        analysisTablePanel = new MeaningfulnessAnalysisTablePanel();

        add(createHeaderPanel(), BorderLayout.PAGE_START);
        add(analysisTablePanel, BorderLayout.CENTER);
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

        analysisTablePanel.showRows(tokenRows);
    }

    public void clear() {
        resultLabel.setText(bundle.getString("meaningfulness.result"));
        scoreLabel.setText(bundle.getString("meaningfulness.score"));
        explanationLabel.setText(bundle.getString("meaningfulness.explanation"));
        analysisTablePanel.clear();
    }
}
