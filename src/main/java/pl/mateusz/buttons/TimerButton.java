package pl.mateusz.buttons;

import java.util.Timer;
import java.util.TimerTask;

public class TimerButton extends AbstractTopButton{
    private static Timer timer;

    public TimerButton() {
        initialConfigure(this,"000");

    }

    public void start() {
        this.setText("000");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int time = Integer.parseInt(getText());
                time++;
                String newTime = String.format("%03d",time);
                setText(newTime);
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void stop() {
        timer.cancel();
        timer = new Timer();
    }


}
