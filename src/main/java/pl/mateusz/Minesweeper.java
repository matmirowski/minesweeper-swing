package pl.mateusz;

import pl.mateusz.buttons.*;
import pl.mateusz.buttons.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class Minesweeper {
    private final Frame frame;
    private final ArrayList<Field> fields = new ArrayList<>();
    private final ResetButton resetButton = new ResetButton();
    private final Stopwatch stopwatch = new Stopwatch();
    private final MineCounter mineCounter = new MineCounter();
    private int bombs = 0;

    public Minesweeper() {
        frame = new Frame();
    }

    public void init() {
        frame.generateComponents();
        frame.addComponentsToPanels(mineCounter, resetButton, stopwatch);
        generateFields(8);
        generateBombs(10);
        generateNumberFields();
        resetButton.addActionListener(e -> restart());
        frame.pack();
        frame.setVisible(true);
    }

    private void generateFields(int size) {
        for(int y=1; y<=size; y++) {
            for(int x=1; x<=size; x++) {
                Field f = new Field(x,y);
                f.setPreferredSize(new Dimension(30,30));
                f.setFocusable(false);
                f.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                fieldMouseEvents(f);
                frame.addComponentsToPanels(f);
                fields.add(f);
            }
        }
    }

    private void generateBombs(int quantity) {
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

    private void generateNumberFields() {
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

    private void checkWin() {
        int matchingFields = 0;
        for (Field field : fields) {
            if (field.getType().equals(FieldType.BOMB) && field.isMarked())
                matchingFields++;
        }
        if (matchingFields == bombs)
            win();
    }

    private void win() {
        stopwatch.stop();
        int score = Integer.parseInt(stopwatch.getText());
        String[] options = {"Yes","No"};

        int choice = JOptionPane.showOptionDialog(null, "You've won! Your score is: " + score
                + " seconds. Do you want to play again?", "Congratulations!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE
                , new ImageIcon("images/icons/icon.png"), options, null);
        if (choice==0)
            restart();
        else
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private void gameOver() {
        for (Field field : fields) {
            field.display();
        }
        resetButton.playLoseAnimation();
        stopwatch.stop();
    }

    private void restart() {
        fields.forEach(Field::reset);
        generateBombs(bombs);
        generateNumberFields();
        resetButton.setIcon(new ImageIcon("images/icons/rb.gif"));
        mineCounter.setText("010");
        stopwatch.stop();
        stopwatch.reset();
    }

    private void emptyFieldsDisplay(int x, int y) {

        // *** finding field ***

        Field f = fields.stream()
                .filter(field -> field.getX_cord()==x)
                .filter(field -> field.getY_cord()==y)
                .findFirst()
                .orElse(null);

        // *** if field doesn't exist OR is marked OR is shown

        if (f==null || f.isMarked() || !f.isHidden())
            return;

        // *** if that's NOT empty field ***

        if (!f.getType().equals(FieldType.EMPTY)) {
            f.display();
            return;
        }

        // *** if field is empty ***

        f.display();
        emptyFieldsDisplay(x+1,y);
        emptyFieldsDisplay(x-1,y);
        emptyFieldsDisplay(x,y+1);
        emptyFieldsDisplay(x,y-1);
        emptyFieldsDisplay(x+1,y+1);
        emptyFieldsDisplay(x+1,y-1);
        emptyFieldsDisplay(x-1,y+1);
        emptyFieldsDisplay(x-1,y-1);
    }

    private void fieldMouseEvents(Field f) {
        f.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                // *** RIGHT CLICK ***

                if (SwingUtilities.isRightMouseButton(me)) {
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
                    checkWin();
                }

                // *** LEFT CLICK ***

                else if (SwingUtilities.isLeftMouseButton(me)) {
                    if (stopwatch.getText().equals("000"))
                        stopwatch.start();

                    if (!f.isHidden()) // if field is already shown
                        return;

                    else if (!f.isMarked() && !f.getType().equals(FieldType.BOMB) && // if field is a numeric field
                            !f.getType().equals(FieldType.EMPTY))
                        f.display();

                    else if (!f.isMarked() && f.getType().equals(FieldType.EMPTY)) // if field is empty
                        emptyFieldsDisplay(f.getX_cord(), f.getY_cord());

                    else if (f.getType().equals(FieldType.BOMB)) { // if you clicked on bomb
                        f.setType(FieldType.EXPLODED);
                        gameOver();
                    }
                }

            }
        });
    }

}
