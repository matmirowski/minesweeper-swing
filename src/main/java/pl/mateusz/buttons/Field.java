package pl.mateusz.buttons;

import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import java.net.URL;

@Setter
@Getter

public class Field extends JButton {
    private final int x_cord;
    private final int y_cord;
    private boolean hidden = true;
    private boolean marked = false;
    private FieldType type = FieldType.EMPTY;
    private static ImageIcon markedIcon;
    private static ImageIcon hiddenIcon;

    public Field(int x, int y) {
        loadIcons();
        x_cord = x;
        y_cord = y;
        this.setIcon(hiddenIcon);
    }

    public void mark() {
        marked = true;
        this.setIcon(markedIcon);
    }

    public void unmark() {
        marked = false;
        this.setIcon(hiddenIcon);
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
        this.setIcon(hiddenIcon);
    }

    private void loadIcons() {
        URL hiddenURL = getClass().getResource("/images/icons/hidden.png");
        if (hiddenURL != null)
            hiddenIcon = new ImageIcon(hiddenURL);

        URL markedURL = getClass().getResource("/images/icons/marked.png");
        if (markedURL != null)
            markedIcon = new ImageIcon(markedURL);
    }

}
