package pl.mateusz.buttons;

import javax.swing.*;
import java.awt.*;

public class ResetButton extends JButton {

    private static Timer timer;

    public ResetButton() { //resetbutton initial configuration
        this.setPreferredSize(new Dimension(40,40));
        this.setIcon(new ImageIcon("images/icons/rb.gif"));
        this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        this.setContentAreaFilled(false);

        timer = new Timer(3300, e -> {
            this.setIcon(new ImageIcon("images/icons/rb_lose_static.png"));
        });
        timer.setRepeats(false);
    }

    public void playLoseAnimation() {
        this.setIcon(new ImageIcon("images/icons/rb_lose.gif"));
        timer.start();
    }

    public void stopLoseAnimation() {
        timer.stop();
    }
}
