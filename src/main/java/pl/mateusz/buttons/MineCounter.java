package pl.mateusz.buttons;

public class MineCounter extends AbstractTopButton {
    public MineCounter() { //minecounter button initial configuration
        initialConfigure(this,"010");
    }

    public void decrease() {
        int value = Integer.parseInt(getText());
        value--;
        if (value > 9 && value < 100)
            setText("0" + value);
        else if (value >= 100)
            setText(String.valueOf(value));
        else
            setText("00" + value);
    }

    public void increase() {
        int value = Integer.parseInt(getText());
        value++;
        if (value > 9 && value < 100)
            setText("0" + value);
        else if (value > 100)
            setText(String.valueOf(value));
        else
            setText("00" + value);
    }
}
