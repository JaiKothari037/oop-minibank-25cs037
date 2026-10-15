import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = Arrays.asList(
            "riya", "aman", "neel", "riya", "diya", "kush", "aman", "bela"
        );

        Set<String> hashSet = new HashSet<String>(logins);
        Set<String> linkedHashSet = new LinkedHashSet<String>(logins);

        System.out.println("Total login attempts: " + logins.size());
        System.out.println("Unique visitors count (HashSet): " + hashSet.size());
        System.out.println("First-seen order (LinkedHashSet): " + linkedHashSet);

        System.out.print("First three distinct names: ");
        int count = 0;
        for (String user : linkedHashSet) {
            System.out.print(user + " ");
            count++;
            if (count == 3) {
                break;
            }
        }
        System.out.println();
    }
}