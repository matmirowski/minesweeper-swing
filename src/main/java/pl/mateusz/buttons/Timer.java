package pl.mateusz.buttons;

public class Timer extends AbstractTopButton{
    public Timer() { //timer button initial configuration
        initialConfigure(this,"000");
    }

    //TODO reset timer
    public void reset() {
        setText("000");
    }
    //TODO start timer
    public void start() {

    }

}
