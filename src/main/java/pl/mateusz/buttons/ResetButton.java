package pl.mateusz.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetButton extends JButton {
    public ResetButton() { //resetbutton initial configuration
        setPreferredSize(new Dimension(40,40));
        setIcon(new ImageIcon("images/icons/rb.gif"));
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setContentAreaFilled(false);
    }

    public void playLoseAnimation() {
        setIcon(new ImageIcon("images/icons/rb_lose.gif"));
        //TODO loseAnimation

    }
}
