package pl.mateusz.buttons;

import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;

@Setter
@Getter

public class Field extends JButton {
    private final int x_cord;
    private final int y_cord;
    private boolean hidden = true;
    private boolean marked = false;
    private FieldType type = FieldType.EMPTY;

    /**
     * Non-numeric icons are stored inside this HashMap, numeric icons are loaded
     * via display() method to avoid multiple lines of boilerplate code.
     * Non-numeric icons are loaded via loadIcons() method.
     * Keys: hidden, marked
     */
    private final static HashMap<String,ImageIcon> nonNumericIcons = new HashMap<>();

    public Field( int x, int y) {
        loadIcons();
        x_cord = x;
        y_cord = y;
        this.setIcon(nonNumericIcons.get("hidden"));
    }

    public void mark() {
        marked = true;
        this.setIcon(nonNumericIcons.get("marked"));
    }

    public void unmark() {
        marked = false;
        this.setIcon(nonNumericIcons.get("hidden"));
    }

    public void display() {
        hidden = false;
        String path = "/images/icons/";
        path += type.toString().toLowerCase();
        path += ".png";
        URL iconURL = getClass().getResource(path);
        if (iconURL != null)
            this.setIcon(new ImageIcon(iconURL));
    }

    public void reset() {
        hidden = true;
        marked = false;
        type = FieldType.EMPTY;
        this.setIcon(nonNumericIcons.get("hidden"));
    }

    public void loadIcons() {
        URL hiddenURL = getClass().getResource("/images/icons/hidden.png");
        if (hiddenURL != null)
            nonNumericIcons.put("hidden", new ImageIcon(hiddenURL));

        URL markedURL = getClass().getResource("/images/icons/marked.png");
        if (markedURL != null)
            nonNumericIcons.put("marked", new ImageIcon(markedURL));
    }

}
