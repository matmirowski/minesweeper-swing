package pl.mateusz.buttons;

import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import java.awt.*;

@Setter
@Getter

public class Field extends JButton {
    private final int x_cord;
    private final int y_cord;
    private boolean hidden = true;
    private boolean marked = false;
    private FieldType type = FieldType.EMPTY;

    public Field( int x, int y) {
        x_cord = x;
        y_cord = y;
        setIcon(new ImageIcon("images/icons/hidden.png"));
    }

    public void mark() {
        marked = true;
        setIcon(new ImageIcon("images/icons/marked.png"));
    }

    public void unmark() {
        marked = false;
        this.setIcon(new ImageIcon("images/icons/hidden.png"));
    }

    public void display() {
        hidden = false;
        String path = "images/icons/";
        path += type.toString().toLowerCase();
        path += ".png";
        setIcon(new ImageIcon(path));
    }

    public void reset() {
        hidden = true;
        marked = false;
        type = FieldType.EMPTY;
        setIcon(new ImageIcon("images/icons/hidden.png"));
    }

}
