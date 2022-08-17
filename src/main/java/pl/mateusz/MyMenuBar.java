package pl.mateusz;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
@Getter
public class MyMenuBar extends JMenuBar {
    private final JMenuItem newItem = new JMenuItem("New");
    private final JCheckBoxMenuItem beginnerItem = new JCheckBoxMenuItem("Beginner");
    private final JCheckBoxMenuItem intermediateItem = new JCheckBoxMenuItem("Intermediate");
    private final JCheckBoxMenuItem expertItem = new JCheckBoxMenuItem("Expert");
    private final JCheckBoxMenuItem customItem = new JCheckBoxMenuItem("Custom...");
    private final JMenuItem bestTimesItem = new JMenuItem("Best Times...");
    private final JMenuItem exitItem = new JMenuItem("Exit");
    MyMenuBar() {
        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("Help");

        this.add(gameMenu);
        this.add(helpMenu);

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

        // TODO temporarily disabled
        bestTimesItem.setEnabled(false);

        beginnerItem.setSelected(true);

        JMenuItem aboutItem = new JMenuItem("About Minesweeper");
        helpMenu.add(aboutItem);

        //TODO temporarily disabled
        helpMenu.setEnabled(false);

    }
}
