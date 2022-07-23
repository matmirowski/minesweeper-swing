package pl.mateusz.buttons;

import javax.swing.*;
import java.awt.*;

public class ResetButton extends JButton {
    private static Timer loseAnimationTimer;
    private static Timer winAnimationTimer;

    public ResetButton() { //resetbutton initial configuration
        this.setPreferredSize(new Dimension(40,40));
        this.setIcon(new ImageIcon("images/icons/rb.gif"));
        this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        this.setContentAreaFilled(false);

        loseAnimationTimer = new Timer(3300, e -> {
            this.setIcon(new ImageIcon("images/icons/rb_lose_static.png"));
        });
        loseAnimationTimer.setRepeats(false);

        winAnimationTimer = new Timer(2100, e -> {
            this.setIcon(new ImageIcon("images/icons/rb_win_static.png"));
        });
        winAnimationTimer.setRepeats(false);
    }

    public void playLoseAnimation() {
        this.setIcon(new ImageIcon("images/icons/rb_lose.gif"));
        loseAnimationTimer.start();
    }

    public void stopLoseAnimation() {
        loseAnimationTimer.stop();
    }

    public void playWinAnimation() {
        this.setIcon(new ImageIcon("images/icons/rb_win.gif"));
        winAnimationTimer.start();
    }

    public void stopWinAnimation() {
        winAnimationTimer.stop();
    }
}
