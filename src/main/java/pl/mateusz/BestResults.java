package pl.mateusz;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class BestResults implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private HashMap<Difficulty, Integer> scores = new HashMap<>();

    public BestResults() {
        scores.put(Difficulty.BEGINNER, 999);
        scores.put(Difficulty.INTERMEDIATE, 999);
        scores.put(Difficulty.EXPERT, 999);
    }
    public int getBeginnerScore() {
        return scores.get(Difficulty.BEGINNER);
    }

    public int getIntermediateScore() {
        return scores.get(Difficulty.INTERMEDIATE);
    }

    public int getExpertScore() {
        return scores.get(Difficulty.EXPERT);
    }
}
