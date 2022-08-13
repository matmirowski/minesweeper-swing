package pl.mateusz;

public enum Difficulty {
    BEGINNER(8,10), INTERMEDIATE(16,40), EXPERT(24,80);

    final int size;
    final int bombs;

    Difficulty(int size, int bombs) {
        this.size = size;
        this.bombs = bombs;
    }
}
