package pl.mateusz;

import lombok.Getter;
import pl.mateusz.buttons.Field;
import pl.mateusz.buttons.MineCounter;
import pl.mateusz.buttons.ResetButton;
import pl.mateusz.buttons.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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

        ImageIcon frameIcon;
        URL iconURL = getClass().getResource("/images/icons/icon.png");
        if (iconURL != null) {
            frameIcon = new ImageIcon(iconURL);
            this.setIconImage(frameIcon.getImage());
        }
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

    public void addComponentsToPanels(MineCounter mc, ResetButton rb, Stopwatch tm) {
        topPanel.add(mc);
        topPanel.add(rb);
        topPanel.add(tm);
    }

    public void addComponentsToPanels(Field f) {
        gamePanel.add(f);
    }
}
