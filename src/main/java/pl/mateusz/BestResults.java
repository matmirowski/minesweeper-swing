package pl.mateusz;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class BestResults implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private final HashMap<Difficulty, Integer> scores = new HashMap<>();
    private final HashMap<Difficulty, String> dates = new HashMap<>();
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm");

    public BestResults() {
        setDefault();
    }

    public int getResult(Difficulty difficulty) {
        return scores.get(difficulty);
    }

    public String getDate(Difficulty difficulty) {
        return dates.get(difficulty);
    }

    public void setDefault() {
        LocalDateTime time = LocalDateTime.now();
        String formatedTime = DTF.format(time);
        scores.put(Difficulty.BEGINNER, 999);
        scores.put(Difficulty.INTERMEDIATE, 999);
        scores.put(Difficulty.EXPERT, 999);
        dates.put(Difficulty.BEGINNER, formatedTime);
        dates.put(Difficulty.INTERMEDIATE, formatedTime);
        dates.put(Difficulty.EXPERT, formatedTime);
    }

    public void updateResult(Difficulty difficulty, int score) {
        scores.put(difficulty, score);
        LocalDateTime time = LocalDateTime.now();
        String formatedTime = DTF.format(time);
        dates.put(difficulty, formatedTime);
    }
}
