package pl.mateusz;

import pl.mateusz.buttons.*;
import pl.mateusz.buttons.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Minesweeper {
    private final Frame frame;
    /** ArrayList containing all game fields */

    private final MyMenuBar menuBar;
    private final ArrayList<Field> fields = new ArrayList<>();
    /** Button playing emoji animations, resets game when clicked */
    private final ResetButton resetButton = new ResetButton();
    /** Displays time of current game, increased by 1 every second */
    private final Stopwatch stopwatch = new Stopwatch();
    /** Displays how many marks does player have left */
    private final MineCounter mineCounter = new MineCounter();
    /** Ammount of bombs "planted" in the current game */
    private int mines;
    private int size;


    /** Constructor, creating a JFrame and configuring MenuBar ActionListeners */
    public Minesweeper() {
        frame = new Frame();
        menuBar = (MyMenuBar) frame.getJMenuBar();
        configureMenuBarActions();
    }

    /** Configuring and generating all game components */
    public void init() {
        frame.generatePanels();
        frame.addComponentsToPanels(mineCounter, resetButton, stopwatch);
        generateFields(8);
        generateBombs(10);
        assignNumbersToFields();
        resetButton.addActionListener(e -> restart());
        frame.setVisible(true);
    }

    /**
     * Generating game fields
     * @param size number of fields in 1 row (if game is 8x8 size should equal 8)
     */
    private void generateFields(int size) {
        for(int y=1; y<=size; y++) {
            for(int x=1; x<=size; x++) {
                Field f = new Field(x,y);
                f.setPreferredSize(new Dimension(30,30));
                f.setFocusable(false);
                f.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                fieldMouseEvents(f);
                frame.getGamePanel().setLayout(new GridLayout(size,size,0,0));
                frame.addComponentsToPanels(f);
                fields.add(f);
                frame.pack();
                this.size = size;
            }
        }
    }

    /**
     * Deletes all fields from gamePanel and fields Arraylist. Used when changing difficulty level
     */
    private void deleteAllFields() {
        fields.clear();
        frame.getGamePanel().removeAll();
    }

    /**
     * Generating random bombs and assigning them to a field.
     * Sets bombs attribute to (quantity).
     * @param quantity ammount of bombs that should be generated.
     */
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
        mines = quantity;
        mineCounter.setValue(quantity);
    }

    /**
     * Checks how many bombs are in proximity of all fields in a game.
     * Assigns a FieldType to all fields.
     */
    private void assignNumbersToFields() {
        for (Field field : fields) {
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

    /**
     * Checks if there is a bomb in the field
     * @param x x coordinate of the field
     * @param y y coordinate of the field
     * @return 1 if field contains a bomb, 0 if field doesn't exist or there is no bomb
     */
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

    /** Runs win() method if all marked fields are used and placed correctly */
    private void checkWin() {
        int matchingFields = 0; // number of marked fields with bomb inside
        for (Field field : fields) {
            if (field.getType().equals(FieldType.BOMB) && field.isMarked())
                matchingFields++;
        }
        if (matchingFields == mines)
            win();
    }

    /** Activated by checkWin() method, displays JOptionPane with score */
    private void win() {
        int score = Integer.parseInt(stopwatch.getText());
        String[] options = {"Yes","No"};
        ImageIcon winOptionPaneIcon = null;

        stopwatch.stopCounting();

        resetButton.playWinAnimation();

        /*
         * Displaying JOptionPane with score and logo icon.
         * Player can either play again (choice=0) or exit (choice=1)
         */

        URL winOptionPaneIconURL = getClass().getResource("/images/icons/icon_spinning.gif");
        if (winOptionPaneIconURL != null)
            winOptionPaneIcon = new ImageIcon(winOptionPaneIconURL);

        int choice = JOptionPane.showOptionDialog(null, "You've won! Your score is: " + score
                + " seconds. Do you want to play again?", "Congratulations!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE
                , winOptionPaneIcon, options, null);

        if (choice==0) // if user wants to play again
            restart();
        else           // if user wants to exit we terminate frame
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Activated when player clicks on the bomb, displays all fields, stops counter,
     * plays losing animation on ResetButton.
     */
    private void lose() {
        for (Field field : fields) {
            field.display();
        }
        resetButton.playLoseAnimation();
        stopwatch.stopCounting();
    }

    /**
     * Resets all components to default state. Triggered by ResetButton.
     */
    private void restart() {
        fields.forEach(Field::reset);
        generateBombs(mines);
        assignNumbersToFields();
        resetButton.stopLoseAnimation();
        resetButton.stopWinAnimation();
        resetButton.playIdleAnimation();
        mineCounter.setValue(mines);
        stopwatch.stopCounting();
        stopwatch.resetCounterValue();
    }

    /**
     * Responsible for exposing all fields near the empty field (FieldType.EMPTY).
     * @param x x coordinate of the field
     * @param y y coordinate of the field
     */
    private void emptyFieldsDisplay(int x, int y) {

        // finding field
        Field f = fields.stream()
                .filter(field -> field.getX_cord()==x)
                .filter(field -> field.getY_cord()==y)
                .findFirst()
                .orElse(null);

        // if field doesn't exist OR is marked OR is shown
        if (f==null || f.isMarked() || !f.isHidden())
            return;

        // if that's NOT empty field
        if (!f.getType().equals(FieldType.EMPTY)) {
            f.display();
            return;
        }

        // if field is empty
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

    /**
     * Handles left and right clicks on a field.
     * Runs leftClickOnField or rightClickOnField method.
     * @param field clicked field
     */
    private void fieldMouseEvents(Field field) {
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                // *** RIGHT CLICK ***
                if (SwingUtilities.isRightMouseButton(me)) {
                    rightClickOnField(field);
                }

                // *** LEFT CLICK ***
                else if (SwingUtilities.isLeftMouseButton(me))
                    leftClickOnField(field);
            }
        });
    }

    /**
     * Handles left click, sets field status.
     * @param field clicked field
     */
    private void leftClickOnField(Field field) {
        // if we didnt start the timer yet, we start it
        if (stopwatch.getText().equals("000"))
            stopwatch.startCounting();

        // if field is already shown or is marked we don't do anything
        if (!field.isHidden() || field.isMarked())
            return;

        // if field is a numeric field we display it
        else if (!field.isMarked() && !field.getType().equals(FieldType.BOMB) &&
                !field.getType().equals(FieldType.EMPTY))
            field.display();

        // if field is empty we start displaying fields nearby
        else if (!field.isMarked() && field.getType().equals(FieldType.EMPTY))
            emptyFieldsDisplay(field.getX_cord(), field.getY_cord());

        // if player clicked on the bomb, it's game over
        else if (field.getType().equals(FieldType.BOMB)) {
            field.setType(FieldType.EXPLODED);
            lose();
        }
    }

    /**
     * Handles right click, sets field status.
     * @param field clicked field
     */
    private void rightClickOnField(Field field) {
        // if we have no more mines
        if ((!field.isMarked() && mineCounter.getText().equals("000")))
            return;

        // if field is already displayed
        if (!field.isHidden())
            return;

        // if field is already marked we unmark it
        else if (field.isMarked()) {
            field.unmark();
            mineCounter.increase();
        }

        // if field isn't marked we can mark it
        else {
            field.mark();
            mineCounter.decrease();
            checkWin();
        }
    }

    /**
     * Assigns tasks to items in MenuBar.
     */
    private void configureMenuBarActions() {
        // You need to remember that when ActionListener is triggered, Selected status is already changed

        // New
        menuBar.getNewItem().addActionListener(e -> restart());

        // Beginner
        menuBar.getBeginnerItem().addActionListener(e -> {
            if (menuBar.getBeginnerItem().isSelected())
                setDifficulty(Difficulty.BEGINNER);
            else
                menuBar.getBeginnerItem().setSelected(true);
        });

        // Intermediate
        menuBar.getIntermediateItem().addActionListener(e -> {
            if (menuBar.getIntermediateItem().isSelected())
                setDifficulty(Difficulty.INTERMEDIATE);
            else
                menuBar.getIntermediateItem().setSelected(true);
        });

        // Expert
        menuBar.getExpertItem().addActionListener(e -> {
            if (menuBar.getExpertItem().isSelected())
                setDifficulty(Difficulty.EXPERT);
            else
                menuBar.getExpertItem().setSelected(true);
        });

        // Custom
        menuBar.getCustomItem().addActionListener(e -> {
            CustomMenuFrame customFrame = new CustomMenuFrame(size, mines);
            if (!customFrame.isCancelled()) {
                int newSize = customFrame.getNewSize();
                int newMines = customFrame.getNewMines();
                setDifficulty(newSize, newMines);
            }
            else {
                menuBar.getCustomItem().setSelected(!menuBar.getCustomItem().isSelected());
            }
        });

        // Best Times
        menuBar.getBestTimesItem().addActionListener(e -> {

        });

        // Exit
        menuBar.getExitItem().addActionListener(e ->
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING))
        );
    }

    /**
     * Changes difficulty of the game.
     * @param difficulty new level of difficulty
     */
    private void setDifficulty(Difficulty difficulty) {
        deleteAllFields();
        switch (difficulty) {
            case BEGINNER -> {
                menuBar.getIntermediateItem().setSelected(false);
                menuBar.getExpertItem().setSelected(false);
                menuBar.getCustomItem().setSelected(false);
            }
            case INTERMEDIATE -> {
                menuBar.getBeginnerItem().setSelected(false);
                menuBar.getExpertItem().setSelected(false);
                menuBar.getCustomItem().setSelected(false);
            }
            case EXPERT -> {
                menuBar.getBeginnerItem().setSelected(false);
                menuBar.getIntermediateItem().setSelected(false);
                menuBar.getCustomItem().setSelected(false);
            }
        }
        generateFields(difficulty.size);
        generateBombs(difficulty.mines);
        assignNumbersToFields();
        restart();
    }

    private void setDifficulty(int size, int mines) {
        menuBar.getCustomItem().setSelected(true);
        menuBar.getBeginnerItem().setSelected(false);
        menuBar.getIntermediateItem().setSelected(false);
        menuBar.getExpertItem().setSelected(false);

        deleteAllFields();
        generateFields(size);
        generateBombs(mines);
        assignNumbersToFields();
        restart();
    }


}
