package pl.mateusz;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class BestResults implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private final HashMap<Difficulty, Integer> scores = new HashMap<>();

    public BestResults() {
        setDefault();
    }

    public int getResult(Difficulty difficulty) {
        return scores.get(difficulty);
    }

    public void setDefault() {
        scores.put(Difficulty.BEGINNER, 999);
        scores.put(Difficulty.INTERMEDIATE, 999);
        scores.put(Difficulty.EXPERT, 999);
    }

    public void updateResult(Difficulty difficulty, int score) {
        scores.put(difficulty, score);
    }
}
