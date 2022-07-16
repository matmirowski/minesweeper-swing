package pl.mateusz.buttons;

import javax.swing.*;
import java.awt.*;

public class ResetButton extends JButton {
    public ResetButton() { //resetbutton initial configuration
        setPreferredSize(new Dimension(40,40));
        setIcon(new ImageIcon("images/icons/rb.gif"));
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setContentAreaFilled(false);
    }
}
