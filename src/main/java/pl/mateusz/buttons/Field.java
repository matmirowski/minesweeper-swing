package pl.mateusz.buttons;

import lombok.Getter;
import lombok.Setter;
import javax.swing.*;

@Setter
@Getter

public class Field extends JButton {
    private int x_cord;
    private int y_cord;
    private boolean bomb = false;
    private boolean hidden = true;
    private boolean marked = false;

    public Field( int x, int y) {
        x_cord = x;
        y_cord = y;
    }

    public void setBomb (boolean bool) {
        if (bool) {
            bomb = true;
            setIcon("Bomb");
        }
        else {
            bomb = false;
            setIcon("Empty");
        }
    }


    public void setIcon(String iconType) {
        switch (iconType) {
            case "Marked" -> {
                this.setIcon(new ImageIcon("images/icons/marked-field.jpg"));
            }
            case "Bomb" -> {
                this.setIcon(new ImageIcon("images/icons/bomb.jpg"));
            }
            case "Empty" -> {
                this.setIcon(new ImageIcon("images/icons/empty-field.jpg"));
            }
        }
    }




}
