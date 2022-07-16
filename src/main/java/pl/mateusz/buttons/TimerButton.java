package pl.mateusz.buttons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerButton extends AbstractTopButton{
    private Timer timer;
    public TimerButton() {
        initialConfigure(this,"000");
    }

    public void start() {
        timer = new Timer(1000, e -> {
            int value = Integer.parseInt(getText());
            if (value==999)
                return;
            value++;
            String newText = String.format("%03d",value);
            setText(newText);
        });
        timer.start();
    }

    public void reset() {
        timer.stop();
        setText("000");
    }

    public void stop() {
        timer.stop();
    }

}
