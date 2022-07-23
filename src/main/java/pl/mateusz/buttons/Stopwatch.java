package pl.mateusz.buttons;

import javax.swing.Timer;

public class Stopwatch extends AbstractTopButton{
    private static Timer timer;

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

        timer = new Timer(1000, e -> {
            int time = Integer.parseInt(getText());

            /* Text in a counter can't surpass 999 */
            if (time != 999)
                time++;

            /* 3 digits, filled with zeros */
            String newTime = String.format("%03d",time);
            setText(newTime);
        });
    }

    public void start() {           /* Starting Timer */
        this.setText("000");
        timer.start();
    }

    public void stop() {            /* Stopping timer */
        timer.stop();
    }

    public void reset() {           /* Setting counter value to 000 */
        this.setText("000");
    }

}
