package pl.mateusz;

import lombok.Getter;
import pl.mateusz.buttons.Field;
import pl.mateusz.buttons.MineCounter;
import pl.mateusz.buttons.ResetButton;
import pl.mateusz.buttons.Timer;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    @Getter
    private JPanel topPanel;
    @Getter
    private JPanel gamePanel;

    public Frame() { //frame configuration
        this.setTitle("Minesweeper");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout(5,3));
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.lightGray));
    }

    public void generateComponents() {
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,3));
        topPanel.setPreferredSize(new Dimension(100,50));
        topPanel.setBorder(BorderFactory.createEtchedBorder());

        gamePanel = new JPanel(new GridLayout(8,8,0,0));
        gamePanel.setBorder(BorderFactory.createEtchedBorder());

        this.add(topPanel,BorderLayout.NORTH);
        this.add(gamePanel,BorderLayout.CENTER);

    }

    public void addComponentsToPanels(MineCounter mc, ResetButton rb, Timer tm) {
        topPanel.add(mc);
        topPanel.add(rb);
        topPanel.add(tm);
    }

    public void addComponentsToPanels(Field f) {
        gamePanel.add(f);
    }
}
