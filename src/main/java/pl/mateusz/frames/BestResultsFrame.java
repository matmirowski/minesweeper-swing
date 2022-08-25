package pl.mateusz.frames;

import pl.mateusz.BestResults;
import pl.mateusz.Difficulty;

import javax.swing.*;
import java.awt.*;

public class BestResultsFrame extends JDialog {
    private final JLabel beginnerResultLabel = new JLabel();
    private final JLabel beginnerDateLabel = new JLabel();
    private final JLabel intermediateResultLabel = new JLabel();
    private final JLabel intermediateDateLabel = new JLabel();
    private final JLabel expertResultLabel = new JLabel();
    private final JLabel expertDateLabel = new JLabel();
    private final BestResults bestResults;

    public BestResultsFrame(BestResults bestResults) {
        this.setModal(true);
        this.setTitle("Best Results");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout(0,2));
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.bestResults = bestResults;

        loadResults();
        configureResultsPanel();
        configureButtonPanel();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void loadResults() {
        int beginnerResult = bestResults.getResult(Difficulty.BEGINNER);
        int intermediateResult = bestResults.getResult(Difficulty.INTERMEDIATE);
        int expertResult = bestResults.getResult(Difficulty.EXPERT);
        String beginnerDate = bestResults.getDate(Difficulty.BEGINNER);
        String intermediateDate = bestResults.getDate(Difficulty.INTERMEDIATE);
        String expertDate = bestResults.getDate(Difficulty.EXPERT);

        beginnerResultLabel.setText(beginnerResult + " seconds");
        beginnerDateLabel.setText(beginnerDate);
        intermediateResultLabel.setText(intermediateResult + " seconds");
        intermediateDateLabel.setText(intermediateDate);
        expertResultLabel.setText(expertResult + " seconds");
        expertDateLabel.setText(expertDate);
    }

    private void configureResultsPanel() {
        JPanel resultsPanel = new JPanel(new GridLayout(3,3,10,10));
        //resultsPanel.setBorder(BorderFactory.createEtchedBorder());
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)));

        resultsPanel.add(new JLabel("Beginner:"));
        resultsPanel.add(beginnerResultLabel);
        resultsPanel.add(beginnerDateLabel);
        resultsPanel.add(new JLabel("Intermediate:"));
        resultsPanel.add(intermediateResultLabel);
        resultsPanel.add(intermediateDateLabel);
        resultsPanel.add(new JLabel("Expert:"));
        resultsPanel.add(expertResultLabel);
        resultsPanel.add(expertDateLabel);
        this.add(resultsPanel, BorderLayout.NORTH);
    }

    private void configureButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // OK
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        // Reset
        JButton resetButton = new JButton("Reset Scores");
        resetButton.addActionListener(e -> {
            int resetAnswer = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to reset all the best times?",
                    "Reset Scores",JOptionPane.YES_NO_OPTION);
            if (resetAnswer == 0) {
                bestResults.setDefault();
                this.loadResults();
            }
        });
        buttonPanel.add(okButton);
        buttonPanel.add(resetButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
