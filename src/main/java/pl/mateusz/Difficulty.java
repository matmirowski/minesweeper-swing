package pl.mateusz;

public enum Difficulty {
    BEGINNER(8,10), INTERMEDIATE(16,40), EXPERT(24,80), CUSTOM(0,0);

    public final int SIZE;
    public final int MINES;

    Difficulty(int SIZE, int MINES) {
        this.SIZE = SIZE;
        this.MINES = MINES;
    }
}
