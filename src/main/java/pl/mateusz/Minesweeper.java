package pl.mateusz;

import pl.mateusz.buttons.*;
import pl.mateusz.buttons.Stopwatch;
import pl.mateusz.frames.BestResultsFrame;
import pl.mateusz.frames.CustomFrame;
import pl.mateusz.frames.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Minesweeper {
    private final pl.mateusz.frames.Frame frame;
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
    private int height;
    private int width;
    private Difficulty difficulty;
    private BestResults bestResults;


    /** Constructor, creating a JFrame and configuring MenuBar ActionListeners */
    public Minesweeper() {
        frame = new Frame();
        // When user closes app we need to serialize best results to file
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                serializeBestResults();
            }
        });
        menuBar = (MyMenuBar) frame.getJMenuBar();
        configureMenuBarActions();
        deserializeBestResults();
    }

    /** Configuring and generating all game components */
    public void init() {
        frame.generatePanels();
        frame.addComponentsToPanels(mineCounter, resetButton, stopwatch);
        setDifficulty(Difficulty.BEGINNER, Difficulty.BEGINNER.HEIGHT,
                Difficulty.BEGINNER.WIDTH, Difficulty.BEGINNER.MINES);
        resetButton.addActionListener(e -> restart());
        frame.setVisible(true);
    }

    /**
     * Generating game fields
     * @param height number of fields in a column
     * @param width number of fields in a row
     */
    private void generateFields(int height, int width) {
        for(int y=1; y<=height; y++) {
            for(int x=1; x<=width; x++) {
                Field f = new Field(x,y);
                f.setPreferredSize(new Dimension(30,30));
                f.setFocusable(false);
                f.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                assignMouseListener(f);
                frame.getGamePanel().setLayout(new GridLayout(height, width,0,0));
                frame.addComponentsToPanels(f);
                fields.add(f);
                frame.pack();
                this.height = height;
                this.width = width;
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

        /* Checking if score is better than currently best score */
        int score = Integer.parseInt(stopwatch.getText());
        if (difficulty != Difficulty.CUSTOM) {
            int currentlyBestResult = bestResults.getResult(difficulty);
            if (score < currentlyBestResult) {
                bestResults.updateResult(difficulty, score);
            }
        }

        stopwatch.stopCounting();
        resetButton.playWinAnimation();

        /*
         * Displaying JOptionPane with score, difficulty and logo icon.
         * Player can either play again (choice=0) or exit (choice=1)
         */
        String[] options = {"Yes","No"};
        ImageIcon winOptionPaneIcon = null;

        URL winOptionPaneIconURL = getClass().getResource("/images/icons/icon_spinning.gif");
        if (winOptionPaneIconURL != null)
            winOptionPaneIcon = new ImageIcon(winOptionPaneIconURL);

        int choice = JOptionPane.showOptionDialog(null, "You've won!\nScore: " + score
                + " seconds. Difficulty: "+ difficulty + "\nDo you want to play again?", "Congratulations!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE
                , winOptionPaneIcon, options, null);

        if (choice==0) // if user wants to play again
            restart();
        else           // if user wants to exit
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
    private void displayEmptyFields(int x, int y) {

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
        displayEmptyFields(x+1,y);
        displayEmptyFields(x-1,y);
        displayEmptyFields(x,y+1);
        displayEmptyFields(x,y-1);
        displayEmptyFields(x+1,y+1);
        displayEmptyFields(x+1,y-1);
        displayEmptyFields(x-1,y+1);
        displayEmptyFields(x-1,y-1);
    }

    /**
     * Handles left and right clicks on a field.
     * Runs leftClickOnField or rightClickOnField method.
     * @param field clicked field
     */
    private void assignMouseListener(Field field) {
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                // *** RIGHT CLICK ***
                if (SwingUtilities.isRightMouseButton(me)) {
                    manageRightClickOnField(field);
                }
                // *** LEFT CLICK ***
                else if (SwingUtilities.isLeftMouseButton(me))
                    manageLeftClickOnField(field);
            }
        });
    }

    /**
     * Handles left click, sets field status.
     * @param field clicked field
     */
    private void manageLeftClickOnField(Field field) {
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
            displayEmptyFields(field.getX_cord(), field.getY_cord());

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
    private void manageRightClickOnField(Field field) {
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
                setDifficulty(Difficulty.BEGINNER, Difficulty.BEGINNER.HEIGHT,
                        Difficulty.BEGINNER.WIDTH, Difficulty.BEGINNER.MINES);
            else
                menuBar.getBeginnerItem().setSelected(true);
        });

        // Intermediate
        menuBar.getIntermediateItem().addActionListener(e -> {
            if (menuBar.getIntermediateItem().isSelected())
                setDifficulty(Difficulty.INTERMEDIATE, Difficulty.INTERMEDIATE.HEIGHT,
                        Difficulty.INTERMEDIATE.WIDTH, Difficulty.INTERMEDIATE.MINES);
            else
                menuBar.getIntermediateItem().setSelected(true);
        });

        // Expert
        menuBar.getExpertItem().addActionListener(e -> {
            if (menuBar.getExpertItem().isSelected())
                setDifficulty(Difficulty.EXPERT, Difficulty.EXPERT.HEIGHT,
                        Difficulty.EXPERT.WIDTH, Difficulty.EXPERT.MINES);
            else
                menuBar.getExpertItem().setSelected(true);
        });

        // Custom
        menuBar.getCustomItem().addActionListener(e -> {
            CustomFrame customFrame = new CustomFrame(height, width, mines);
            if (!customFrame.isCancelled()) {
                int newHeight = customFrame.getNewHeight();
                int newWidth = customFrame.getNewWidth();
                int newMines = customFrame.getNewMines();
                setDifficulty(Difficulty.CUSTOM, newHeight, newWidth, newMines);
            }
            else {
                menuBar.getCustomItem().setSelected(!menuBar.getCustomItem().isSelected());
            }
        });

        // Best Times
        menuBar.getBestTimesItem().addActionListener(e -> {
            BestResultsFrame scoresFrame = new BestResultsFrame(bestResults);
        });

        // Exit
        menuBar.getExitItem().addActionListener(e -> {
            serializeBestResults();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        );
    }

    /**
     * Changes difficulty of the game.
     * @param difficulty new level of difficulty
     */
    private void setDifficulty(Difficulty difficulty, int height, int width, int mines) {
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
            case CUSTOM -> {
                menuBar.getCustomItem().setSelected(true);
                menuBar.getBeginnerItem().setSelected(false);
                menuBar.getIntermediateItem().setSelected(false);
                menuBar.getExpertItem().setSelected(false);
            }
        }
        this.difficulty = difficulty;
        deleteAllFields();
        generateFields(height, width);
        generateBombs(mines);
        assignNumbersToFields();
        restart();
    }

    private void deserializeBestResults() {
        try (FileInputStream fileIn = new FileInputStream("results.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            bestResults = (BestResults) in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            bestResults = new BestResults();
        }
    }

    private void serializeBestResults() {
        try (FileOutputStream fileOut = new FileOutputStream("results.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(bestResults);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
