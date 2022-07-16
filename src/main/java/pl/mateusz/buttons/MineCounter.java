package pl.mateusz.buttons;

public class MineCounter extends AbstractTopButton {
    public MineCounter() {
        initialConfigure(this,"010");
    }

    public void decrease() {
        int value = Integer.parseInt(getText());
        value--;
        String newText = String.format("%03d",value);
        setText(newText);
    }

    public void increase() {
        int value = Integer.parseInt(getText());
        value++;
        String newText = String.format("%03d",value);
        setText(newText);
    }
}
