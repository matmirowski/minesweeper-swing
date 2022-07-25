package pl.mateusz.buttons;

import javax.swing.Timer;
import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class Stopwatch extends AbstractTopButton{
    private static Timer counter;

    public Stopwatch() {

        /*
         * Configuring Stopwatch:
         * - Setting layouts, size, colors, alignment
         */

        initialConfigure(this,"000");

        /*
         * Timer repeatable task
         * Icreasing counter value by 1
         */

        counter = new Timer(1000, e -> {
            int time = Integer.parseInt(getText());

            /* Text in a counter can't surpass 999 */
            if (time != 999)
                time++;

            /* 3 digits, filled with zeros */
            String newTime = String.format("%03d",time);
            setText(newTime);
        });
    }

    public void startCounting() {
        this.setText("000");
        counter.start();
    }

    public void stopCounting() {
        counter.stop();
    }

    public void resetCounterValue() {
        this.setText("000");
    }

}
