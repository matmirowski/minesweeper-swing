package pl.mateusz;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BestResultsFrame extends JDialog {
    @Getter
    private boolean reset = false;

    public BestResultsFrame(int beginnerResult, int intermediateResult, int expertResult) {
        this.setModal(true);
        this.setTitle("Best Results");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout(0,0));
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        /* Results Panel */
        JPanel resultsPanel = new JPanel(new GridLayout(3,3,10,10));
        resultsPanel.setBorder(BorderFactory.createEtchedBorder());

        JLabel beginnerLabel = new JLabel("Beginner:");
        JLabel intermediateLabel = new JLabel("Intermediate:");
        JLabel expertLabel = new JLabel("Expert:");
        JLabel beginnerResultLabel = new JLabel(String.valueOf(beginnerResult) + " seconds");
        JLabel intermediateResultLabel = new JLabel(String.valueOf(intermediateResult) + " seconds");
        JLabel expertResultLabel = new JLabel(String.valueOf(expertResult) + " seconds");

        resultsPanel.add(beginnerLabel);
        resultsPanel.add(beginnerResultLabel);
        resultsPanel.add(intermediateLabel);
        resultsPanel.add(intermediateResultLabel);
        resultsPanel.add(expertLabel);
        resultsPanel.add(expertResultLabel);


        /* Button Panel */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton okButton = new JButton("OK");
        JButton resetButton = new JButton("Reset Scores");

        okButton.addActionListener(e -> dispose());

        resetButton.addActionListener(e -> {
            int resetAnswer = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to reset all the best times?",
                    "Reset Scores",JOptionPane.YES_NO_OPTION);
            if (resetAnswer == 0) {

            }

        });

        buttonPanel.add(okButton);
        buttonPanel.add(resetButton);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        this.add(resultsPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
