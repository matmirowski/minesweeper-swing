package pl.mateusz.buttons;

public class MineCounter extends AbstractTopButton {
    public MineCounter() {
        initialConfigure(this,"010");
    }

    /**
     * Decreasing value of displayed text by 1
     * Should be triggered when player marks a field
     */
    public void decrease() {
        int value = Integer.parseInt(getText());
        value--;
        String newText = String.format("%03d",value);
        setText(newText);
    }

    /**
     * Increasing value of displayed text by 1
     * Should be triggered when player unmarks a field
     */
    public void increase() {
        int value = Integer.parseInt(getText());
        value++;
        String newText = String.format("%03d",value);
        setText(newText);
    }
}
