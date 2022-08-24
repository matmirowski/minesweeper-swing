package pl.mateusz.frames;

import lombok.Getter;
import pl.mateusz.MyMenuBar;
import pl.mateusz.buttons.Field;
import pl.mateusz.buttons.MineCounter;
import pl.mateusz.buttons.ResetButton;
import pl.mateusz.buttons.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Frame extends JFrame {
    // Contains topPanel and gamePanel, everything inside the frame, except menubar
    private final JPanel mainPanel = new JPanel(new BorderLayout(0,2));
    @Getter
    // Contains MineCounter, Stopwatch and ResetButton
    private JPanel topPanel;
    @Getter
    // Contains game fields
    private JPanel gamePanel;

    public Frame() {

        // Adapts style from current system (makes fonts and buttons modern)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Frame and mainPanel initial configuration (title, borders, menubar)
        this.setTitle("Minesweeper");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout(0,0));
        this.setJMenuBar(new MyMenuBar());
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        mainPanel.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.lightGray));
        mainPanel.setBackground(Color.lightGray);

        // Loading icon
        ImageIcon frameIcon;
        URL iconURL = getClass().getResource("/images/icons/icon.png");
        if (iconURL != null) {
            frameIcon = new ImageIcon(iconURL);
            this.setIconImage(frameIcon.getImage());
        }
    }

    public void generatePanels() {
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,3));
        topPanel.setPreferredSize(new Dimension(100,50));
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        topPanel.setBackground(new Color(185,185,185));

        gamePanel = new JPanel(new GridLayout(8,8,0,0));
        gamePanel.setBorder(BorderFactory.createEtchedBorder());

        mainPanel.add(topPanel,BorderLayout.NORTH);
        mainPanel.add(gamePanel,BorderLayout.CENTER);
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
