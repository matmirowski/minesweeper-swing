package pl.mateusz;

import javax.swing.*;

public class MyMenuBar extends JMenuBar {

    MyMenuBar() {
        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem beginnerItem = new JMenuItem("Beginner");
        JMenuItem intermediateItem = new JMenuItem("Intermediate");
        JMenuItem expertItem = new JMenuItem("Expert");
        JMenuItem customItem = new JMenuItem("Custom...");
        JMenuItem bestTimesItem = new JMenuItem("Best Times...");
        JMenuItem exitItem = new JMenuItem("Exit");

        gameMenu.add(newItem);
        gameMenu.add(beginnerItem);
        gameMenu.add(intermediateItem);
        gameMenu.add(expertItem);
        gameMenu.add(customItem);
        gameMenu.add(bestTimesItem);
        gameMenu.add(exitItem);
    }
}
