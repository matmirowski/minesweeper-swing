package pl.mateusz;

import javax.swing.*;

public class MyMenuBar extends JMenuBar {

    MyMenuBar() {
        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("Help");

        this.add(gameMenu);
        this.add(helpMenu);

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem beginnerItem = new JMenuItem("Beginner");
        beginnerItem.setEnabled(true);
        JMenuItem intermediateItem = new JMenuItem("Intermediate");
        JMenuItem expertItem = new JMenuItem("Expert");
        JMenuItem customItem = new JMenuItem("Custom...");
        JMenuItem bestTimesItem = new JMenuItem("Best Times...");
        JMenuItem exitItem = new JMenuItem("Exit");

        gameMenu.add(newItem);
        gameMenu.addSeparator();
        gameMenu.add(beginnerItem);
        gameMenu.add(intermediateItem);
        gameMenu.add(expertItem);
        gameMenu.add(customItem);
        gameMenu.addSeparator();
        gameMenu.add(bestTimesItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);

        JMenuItem aboutItem = new JMenuItem("About Minesweeper");
        helpMenu.add(aboutItem);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
