package pl.mateusz.buttons;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;

public class ResetButton extends JButton {

    /** Changing icon to static .png image after lose gif was played */
    private static Timer loseAnimationTimer;

    /** Changing icon to static .png image after win gif was played */
    private static Timer winAnimationTimer;

    /**
     * All icons are loaded via loadIcons() method.
     * Keys: idleGIF, winGIF, winPNG, loseGIF, losePNG
     */
    private static final HashMap<String,ImageIcon> icons = new HashMap<>();

    public ResetButton() {

        /*
         * Configuring resetButton:
         * - Setting borders, size, loading icons
         */

        loadIcons();
        playIdleAnimation();
        this.setPreferredSize(new Dimension(40,40));
        this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        this.setContentAreaFilled(false);

        /*
         * loseAnimationTimer and winAnimationTimer:
         *  - Changing icon to static .png image after gif was played
         *  - delay - duration of gif (gifs are looped)
         *  - all Timers are played only once (setRepeats(false)
         */

        loseAnimationTimer = new Timer(3300, e -> {
            this.setIcon(icons.get("losePNG"));
        });
        loseAnimationTimer.setRepeats(false);

        winAnimationTimer = new Timer(2100, e -> {
            this.setIcon(icons.get("winPNG"));
        });
        winAnimationTimer.setRepeats(false);
    }

    public void playLoseAnimation() {
        this.setIcon(icons.get("loseGIF"));
        loseAnimationTimer.start();
    }

    public void stopLoseAnimation() {
        loseAnimationTimer.stop();
    }

    public void playWinAnimation() {
        this.setIcon(icons.get("winGIF"));
        winAnimationTimer.start();
    }

    public void stopWinAnimation() {
        winAnimationTimer.stop();
    }

    public void playIdleAnimation() {
        this.setIcon(icons.get("idleGIF"));
    }

    private void loadIcons() {
        URL loseGIFURL = getClass().getResource("/images/icons/rb_lose.gif");
        if (loseGIFURL != null)
            icons.put("loseGIF",new ImageIcon(loseGIFURL));

        URL losePNGURL = getClass().getResource("/images/icons/rb_lose_static.png");
        if (losePNGURL != null)
            icons.put("losePNG",new ImageIcon(losePNGURL));

        URL winGIFURL = getClass().getResource("/images/icons/rb_win.gif");
        if (winGIFURL != null)
            icons.put("winGIF",new ImageIcon(winGIFURL));

        URL winPNGURL = getClass().getResource("/images/icons/rb_win_static.png");
        if (winPNGURL != null)
            icons.put("winPNG",new ImageIcon(winPNGURL));

        URL idleGIFURL = getClass().getResource("/images/icons/rb.gif");
        if (idleGIFURL != null)
            icons.put("idleGIF",new ImageIcon(idleGIFURL));
    }
}
