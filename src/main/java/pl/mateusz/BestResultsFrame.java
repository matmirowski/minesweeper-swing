package pl.mateusz;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BestResultsFrame extends JDialog {
    @Getter
    private int beginnerResult;
    private int intermediateResult;
    private int expertResult;
    private JLabel beginnerResultLabel = new JLabel();
    private JLabel intermediateResultLabel = new JLabel();
    private JLabel expertResultLabel = new JLabel();
    private BestResults bestResults;
    
    public BestResultsFrame(BestResults bestResults) {
        this.setModal(true);
        this.setTitle("Best Results");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout(0,0));
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        this.bestResults = bestResults;

        loadResults();
        configureResultsPanel();
        configureButtonPanel();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void loadResults() {
        this.beginnerResult = bestResults.getBeginnerScore();
        this.intermediateResult = bestResults.getIntermediateScore();
        this.expertResult = bestResults.getExpertScore();

        beginnerResultLabel.setText(beginnerResult + " seconds");
        intermediateResultLabel.setText(intermediateResult + " seconds");
        expertResultLabel.setText(expertResult + " seconds");
    }

    private void configureResultsPanel() {
        JPanel resultsPanel = new JPanel(new GridLayout(3,3,10,10));
        resultsPanel.setBorder(BorderFactory.createEtchedBorder());

        resultsPanel.add(new JLabel("Beginner:"));
        resultsPanel.add(beginnerResultLabel);
        resultsPanel.add(new JLabel("Intermediate:"));
        resultsPanel.add(intermediateResultLabel);
        resultsPanel.add(new JLabel("Expert:"));
        resultsPanel.add(expertResultLabel);
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