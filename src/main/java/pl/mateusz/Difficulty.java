package pl.mateusz;

public enum Difficulty {
    BEGINNER(8,8,10), INTERMEDIATE(16,16,40),
    EXPERT(16, 30,99), CUSTOM(0,0,0);

    public final int HEIGHT;
    public final int WIDTH;
    public final int MINES;

    Difficulty(int HEIGHT, int WIDTH, int MINES) {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.MINES = MINES;
    }
}
