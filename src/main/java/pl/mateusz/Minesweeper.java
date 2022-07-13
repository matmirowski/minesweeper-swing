package pl.mateusz;

import lombok.Getter;
import pl.mateusz.buttons.Field;
import pl.mateusz.buttons.MineCounter;
import pl.mateusz.buttons.ResetButton;
import pl.mateusz.buttons.Timer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Minesweeper {
    private final Frame frame;
    private final ArrayList<Field> fields = new ArrayList<>();
    private final ResetButton resetButton = new ResetButton();
    private final Timer timer = new Timer();
    private final MineCounter mineCounter = new MineCounter();

    public Minesweeper() {
        frame = new Frame();
        frame.generateComponents();
        frame.addComponentsToPanels(mineCounter, resetButton, timer);
        generateFields(8);
        frame.pack();
        frame.setVisible(true);
    }


    public void generateFields(int size) {
        for(int y=1; y<=size; y++) {
            for(int x=1; x<=size; x++) {
                Field f = new Field(x,y);
                f.setPreferredSize(new Dimension(30,30));
                f.setFocusable(false);
                frame.addComponentsToPanels(f);
                fields.add(f);
            }
        }
    }

}
