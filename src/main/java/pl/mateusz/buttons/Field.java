package pl.mateusz.buttons;

import lombok.Getter;
import lombok.Setter;
import javax.swing.*;

@Setter
@Getter

public class Field extends JButton {
    private int x_cord;
    private int y_cord;
    private boolean isBomb = false;
    private boolean isHidden = true;

    public Field( int x, int y) {
        x_cord = x;
        y_cord = y;
    }

    public void setBomb (boolean bool) {
        if (bool) {
            isBomb = true;
            ImageIcon icon = new ImageIcon("images/icons/bomb.jpg");
            this.setIcon(icon);
        }
        else {
            isBomb = false;
            ImageIcon icon = new ImageIcon("images/icons/empty-field.jpg");
        }
    }



}
