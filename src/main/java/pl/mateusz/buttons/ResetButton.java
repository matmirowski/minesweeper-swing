package pl.mateusz.buttons;

import javax.swing.*;
import java.awt.*;

public class ResetButton extends JButton {
    private static Timer loseAnimationTimer; // Changing icon to static .png image after lose gif was played
    private static Timer winAnimationTimer; // Changing icon to static .png image after win gif was played

    public ResetButton() {

        /*
         * Configuring resetButton:
         * - Setting borders, size, initial icon
         */

        this.setPreferredSize(new Dimension(40,40));
        this.setIcon(new ImageIcon("images/icons/rb.gif"));
        this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        this.setContentAreaFilled(false);

        /*
         * loseAnimationTimer and winAnimationTimer:
         *  - Changing icon to static .png image after gif was played
         *  - delay - duration of gif (gifs are looped)
         *  - all Timers are played only once (setRepeats(false)
         */

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
