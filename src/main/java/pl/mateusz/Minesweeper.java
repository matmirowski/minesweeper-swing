package pl.mateusz;

import pl.mateusz.buttons.Field;
import pl.mateusz.buttons.MineCounter;
import pl.mateusz.buttons.ResetButton;
import pl.mateusz.buttons.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Minesweeper {
    private final Frame frame;
    private final ArrayList<Field> fields = new ArrayList<>();
    private final ResetButton resetButton = new ResetButton();
    private final Timer timer = new Timer();
    private final MineCounter mineCounter = new MineCounter();

    public Minesweeper() {
        frame = new Frame();
        init();
    }

    private void init() {
        frame.generateComponents();
        frame.addComponentsToPanels(mineCounter, resetButton, timer);
        generateFields(8);
        generateBombs(10);
        generateNumberFields();
        frame.pack();
        frame.setVisible(true);
    }


    public void generateFields(int size) {
        for(int y=1; y<=size; y++) {
            for(int x=1; x<=size; x++) {
                Field f = new Field(x,y);
                f.setPreferredSize(new Dimension(30,30));
                f.setFocusable(false);
                f.setIcon("Hidden");
                fieldMouseEvents(f);
                frame.addComponentsToPanels(f);
                fields.add(f);
            }
        }
    }

    public void generateBombs(int quantity) {
        int size = fields.size();
        Random random = new Random();
        for (int i=0; i<quantity; i++) {
            boolean occupied = true;
            do {
                int bombIndex = random.nextInt(size);
                Field bombField = fields.get(bombIndex);
                if (!bombField.isBomb()) {
                    bombField.setBomb(true);
                    occupied = false;
                }
            } while (occupied);
        }
    }

    public void generateNumberFields() {
        for (Field field : fields) { // for each field
            if (field.isBomb())
                continue;

            int minesAround = 0;
            int x = field.getX_cord();
            int y = field.getY_cord();

            minesAround += checkBombPlacement(x+1,y); // left
            minesAround += checkBombPlacement(x-1,y); // right
            minesAround += checkBombPlacement(x,y-1); // top
            minesAround += checkBombPlacement(x,y+1); // bottom
            minesAround += checkBombPlacement(x-1,y-1); // top left
            minesAround += checkBombPlacement(x+1,y-1); // top right
            minesAround += checkBombPlacement(x-1,y+1); // bottom left
            minesAround += checkBombPlacement(x+1,y+1); // bottom right

            field.setIcon(String.valueOf(minesAround));
        }
    }

    private int checkBombPlacement(int x, int y) {
        Field field = fields.stream()
                    .filter(f -> f.getX_cord()==x)
                    .filter(f -> f.getY_cord()==y)
                    .findFirst()
                    .orElse(null);
        if (field != null && field.isBomb())
            return 1;
        return 0;
    }

    private void fieldMouseEvents(Field f) {
        f.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)) { // right click
                    if (f.isMarked()) {
                        f.setMarked(false);
                        f.setIcon("Hidden");
                        mineCounter.increase();
                    }
                    else {
                        if (!mineCounter.getText().equals("000")) {
                            f.setMarked(true);
                            f.setIcon("Marked");
                            mineCounter.decrease();
                        }

                    }
                }
                else if (SwingUtilities.isLeftMouseButton(me)) { // left click

                }
            }
        });
    }

}
