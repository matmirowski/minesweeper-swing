package pl.mateusz.frames;

import lombok.Getter;
import pl.mateusz.CustomMenuException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
public class CustomFrame extends JDialog {
    private int newHeight;
    private int newWidth;
    private int newMines;
    private boolean cancelled;
    private final JTextField heightTextField;
    private final JTextField widthTextField;
    private final JTextField minesTextField;
    private static final int MIN_HEIGHT = 1;
    private static final int MAX_HEIGHT = 31;
    private static final int MIN_WIDTH = 8;
    private static final int MAX_WIDTH = 64;

    public CustomFrame(int currentHeight, int currentWidth, int currentMines) {
        this.setModal(true);
        this.setTitle("Custom Field");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new GridLayout(3,3,10,10));
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        heightTextField = new JTextField(String.valueOf(currentHeight));
        widthTextField = new JTextField(String.valueOf(currentWidth));
        minesTextField = new JTextField(String.valueOf(currentMines));

        manageActionListeners(okButton, cancelButton);

        this.add(new JLabel("Height:"));
        this.add(heightTextField);
        this.add(okButton);
        this.add(new JLabel("Width:"));
        this.add(widthTextField);
        this.add(cancelButton);
        this.add(new JLabel("Mines:"));
        this.add(minesTextField);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private boolean checkInputValues() {
        String errorMessage;
        try {
            int height = Integer.parseInt(heightTextField.getText());
            int width = Integer.parseInt(widthTextField.getText());
            int mines =Integer.parseInt(minesTextField.getText());
            if (height < MIN_HEIGHT)
                throw new CustomMenuException("Height can't be less than 1!");
            else if (width < MIN_WIDTH)
                throw new CustomMenuException("Width can't be less than 8!");
            else if (height > MAX_HEIGHT)
                throw new CustomMenuException("Height can't be greater than 31.");
            else if (width > MAX_WIDTH)
                throw new CustomMenuException("Width can't be greater than 64.");
            else if (mines > height*width)
                throw new CustomMenuException("Amount of mines can't exceed number of fields.");
            else if (mines < 1)
                throw new CustomMenuException("Amount of mines must be greater than 0.");
            else if (mines > 999)
                throw new CustomMenuException("Amount of mines can't be greater than 999.");
            return true;
        }
        catch (NumberFormatException e) {
            errorMessage = "Invalid number format!";
        }
        catch (CustomMenuException e) {
            errorMessage = e.getMessage();
        }
        JOptionPane.showMessageDialog(null, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    private void manageActionListeners(JButton okButton, JButton cancelButton) {
        okButton.addActionListener(e -> {
            if (checkInputValues()) {
                int height = Integer.parseInt(heightTextField.getText());
                int width = Integer.parseInt(widthTextField.getText());
                int mines = Integer.parseInt(minesTextField.getText());
                this.newHeight = height;
                this.newWidth = width;
                this.newMines = mines;
                this.cancelled = false;
                dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            this.cancelled = true;
            dispose();
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelled = true;
                dispose();
            }
        });
    }
}
