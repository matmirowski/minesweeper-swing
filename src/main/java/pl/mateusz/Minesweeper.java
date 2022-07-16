package pl.mateusz;

import pl.mateusz.buttons.*;
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
    private int bombs = 0;

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
        resetButton.addActionListener(e -> restart());
        frame.pack();
        frame.setVisible(true);
    }

    public void generateFields(int size) {
        for(int y=1; y<=size; y++) {
            for(int x=1; x<=size; x++) {
                Field f = new Field(x,y);
                f.setPreferredSize(new Dimension(30,30));
                f.setFocusable(false);
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
                Field field = fields.get(bombIndex);
                if (field.getType().equals(FieldType.EMPTY)) {
                    field.setType(FieldType.BOMB);
                    occupied = false;
                }
            } while (occupied);
        }
        bombs = quantity;
    }

    public void generateNumberFields() {
        for (Field field : fields) { // for each field
            if (field.getType().equals(FieldType.BOMB))
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

            switch (minesAround) {
                case 1 -> field.setType(FieldType.ONE);
                case 2 -> field.setType(FieldType.TWO);
                case 3 -> field.setType(FieldType.THREE);
                case 4 -> field.setType(FieldType.FOUR);
                case 5 -> field.setType(FieldType.FIVE);
                case 6 -> field.setType(FieldType.SIX);
                case 7 -> field.setType(FieldType.SEVEN);
                case 8 -> field.setType(FieldType.EIGHT);
            }
        }
    }

    private int checkBombPlacement(int x, int y) {
        Field field = fields.stream()
                    .filter(f -> f.getX_cord()==x)
                    .filter(f -> f.getY_cord()==y)
                    .findFirst()
                    .orElse(null);
        if (field != null && field.getType().equals(FieldType.BOMB))
            return 1;
        return 0;
    }

    private boolean checkWin() {
        int matchingFields = 0;
        for (Field field : fields) {
            if (field.getType().equals(FieldType.BOMB) && field.isMarked())
                matchingFields++;
        }
        if (matchingFields == bombs)
            return true;
        return false;
    }

    private void gameOver() {
        for (Field field : fields) {
            field.display();
        }
        resetButton.setIcon(new ImageIcon("images/icons/rb_lose.png"));
    }

    //TODO add restart function
    private void restart() {
        fields.forEach(Field::reset);
        generateBombs(bombs);
        generateNumberFields();
        resetButton.setIcon(new ImageIcon("images/icons/rb.png"));
        mineCounter.setText("010");
    }

    private void fieldMouseEvents(Field f) {
        f.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {


                if (SwingUtilities.isRightMouseButton(me)) { // *** RIGHT CLICK ***
                    if ((!f.isMarked() && mineCounter.getText().equals("000"))) // if we have no more mines
                        return;
                    if (!f.isHidden()) // if field is already shown
                        return;
                    else if (f.isMarked()) { // if field is already marked we unmark it
                        f.unmark();
                        mineCounter.increase();
                    }
                    else { // if field isn't marked we can mark it
                        f.mark();
                        mineCounter.decrease();
                    }

                    //TODO add win case
                    if (checkWin()) { // if WIN
                    }
                }


                else if (SwingUtilities.isLeftMouseButton(me)) { // *** LEFT CLICK ***
                    if (!f.isHidden()) // if field is already shown
                        return;
                    else if (!f.isMarked() && !f.getType().equals(FieldType.BOMB)) // if field isn't bomb / marked
                        f.display();
                    else if (f.getType().equals(FieldType.BOMB)) { // if you clicked on bomb
                        f.setType(FieldType.EXPLODED);
                        gameOver();
                    }
                }

            }
        });
    }

}
