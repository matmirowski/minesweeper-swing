package pl.mateusz;

public class Main {

    public static void main(String[] args){
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.init();
        //TODO lose restartbutton animation is being played only once
        //TODO lpm/rpm animations
        //TODO getClass().getResource("/Boom.gif"); // move icons to resources
        //TODO timer nie dziala za dobrze, czasem po lose sie nie kasuje, przyspiesza XD
        // użyj tam TimerTask
        //TODO wsadź MouseListenera do innej klasy
        //TODO zapisuj wyniki za pomocą serialization

    }
}
