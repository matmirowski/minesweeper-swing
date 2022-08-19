package pl.mateusz;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
public class CustomFrame extends JDialog {
    private int newSize;
    private int newMines;
    private boolean cancelled;
    private final JTextField sizeTextField;
    private final JTextField minesTextField;
    private static final int MAX_SIZE = 31;
    private static final int MIN_SIZE = 8;

    public CustomFrame(int currentSize, int currentMines) {
        this.setModal(true);
        this.setTitle("Custom Field");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new GridLayout(2,3,10,10));
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel sizeLabel = new JLabel("Size:");
        JLabel minesLabel = new JLabel("Mines:");
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        sizeTextField = new JTextField(String.valueOf(currentSize));
        minesTextField = new JTextField(String.valueOf(currentMines));

        okButton.addActionListener(e -> {
            if (checkInputValues()) {
                int size = Integer.parseInt(sizeTextField.getText());
                int mines = Integer.parseInt(minesTextField.getText());
                this.newSize = size;
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

        this.add(sizeLabel);
        this.add(sizeTextField);
        this.add(okButton);
        this.add(minesLabel);
        this.add(minesTextField);
        this.add(cancelButton);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private boolean checkInputValues() {
        String errorMessage;
        try {
            int size = Integer.parseInt(sizeTextField.getText());
            int mines =Integer.parseInt(minesTextField.getText());
            if (size < MIN_SIZE)
                throw new CustomMenuException("Size can't be less than 8!");
            else if (size > MAX_SIZE)
                throw new CustomMenuException("Size can't be greater than 31.");
            else if (mines > size*size)
                throw new CustomMenuException("Amount of mines can't exceed number of fields.");
            else if (mines < 1)
                throw new CustomMenuException("Amount of mines must be greater than 0.");
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

}
