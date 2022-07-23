package pl.mateusz.buttons;

import javax.swing.Timer;

public class TimerButton extends AbstractTopButton{
    private static Timer timer;
    private boolean wasTimerActivated = false;

    public TimerButton() {
        initialConfigure(this,"000");

        timer = new Timer(1000, e -> {
            int time = Integer.parseInt(getText());
            time++;
            String newTime = String.format("%03d",time);
            setText(newTime);
        });
    }

    public void start() {
        this.setText("000");
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void restart() {
        this.setText("000");
        timer.restart();
    }

    public void reset() {
        this.setText("000");
    }

}
