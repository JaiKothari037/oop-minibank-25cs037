import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaderboard {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<String, Integer>();
        scores.put("Riya", 850);
        scores.put("Aman", 920);
        scores.put("Neel", 920);
        scores.put("Diya", 780);
        scores.put("Kush", 850);
        scores.put("Bela", 990);

        int n = 4;

        List<Map.Entry<String, Integer>> leaderboard = new ArrayList<Map.Entry<String, Integer>>(scores.entrySet());

        Collections.sort(leaderboard, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                int scoreCompare = e2.getValue().compareTo(e1.getValue());
                if (scoreCompare != 0) {
                    return scoreCompare;
                }
                return e1.getKey().compareTo(e2.getKey());
            }
        });

        System.out.println("Top " + n + " Leaderboard:");
        int rank = 1;
        for (int i = 0; i < Math.min(n, leaderboard.size()); i++) {
            Map.Entry<String, Integer> entry = leaderboard.get(i);
            System.out.println(rank + ". " + entry.getKey() + " - " + entry.getValue() + " pts");
            rank++;
        }
    }
}