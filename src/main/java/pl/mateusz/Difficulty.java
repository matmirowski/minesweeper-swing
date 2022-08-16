package pl.mateusz;

public enum Difficulty {
    BEGINNER(8,10), INTERMEDIATE(16,40), EXPERT(24,80);

    final int size;
    final int mines;

    Difficulty(int size, int mines) {
        this.size = size;
        this.mines = mines;
    }
}
