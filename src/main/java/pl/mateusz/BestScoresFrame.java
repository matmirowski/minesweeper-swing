package pl.mateusz;

import javax.swing.*;
import java.awt.*;

public class BestScoresFrame extends JDialog {
    private boolean reset;

    public BestScoresFrame(int beginnerScore, int intermediateScore, int expertScore) {
        this.setModal(true);
        this.setTitle("Custom Field");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // TODO temporary - change close operation
        // this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        //this.setLayout(new GridLayout(2,3,10,10));
        this.setLayout(new BorderLayout(0,0));
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel scoresPanel = new JPanel(new GridLayout(3,3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));


        this.add(scoresPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);



    }
}
