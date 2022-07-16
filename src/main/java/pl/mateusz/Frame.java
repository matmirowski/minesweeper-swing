package pl.mateusz;

import lombok.Getter;
import pl.mateusz.buttons.Field;
import pl.mateusz.buttons.MineCounter;
import pl.mateusz.buttons.ResetButton;
import pl.mateusz.buttons.TimerButton;

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
        this.setLayout(new BorderLayout(5,0));
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.lightGray));

        ImageIcon img = new ImageIcon("images/icons/icon.png");
        this.setIconImage(img.getImage());
    }

    public void generateComponents() {
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,3));
        topPanel.setPreferredSize(new Dimension(100,50));
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        topPanel.setBackground(new Color(185,185,185));

        gamePanel = new JPanel(new GridLayout(8,8,0,0));
        gamePanel.setBorder(BorderFactory.createEtchedBorder());

        this.add(topPanel,BorderLayout.NORTH);
        this.add(gamePanel,BorderLayout.CENTER);

    }

    public void addComponentsToPanels(MineCounter mc, ResetButton rb, TimerButton tm) {
        topPanel.add(mc);
        topPanel.add(rb);
        topPanel.add(tm);
    }

    public void addComponentsToPanels(Field f) {
        gamePanel.add(f);
    }
}
