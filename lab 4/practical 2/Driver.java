import java.util.Scanner;

public class Driver
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String keyword = sc.next();

        String[] loglines = {
            "10:05 alice Hello there",
            "11:45 rudolph",
            "01:30 christian My name is christian"
        };

        int counter = 0;
        for (int i = 0; i<3; i++)
        {
            String[] current =  loglines[i].split(" ", 3);
            if (current.length < 3)
            {
                continue;
            }
            for (int j = 0; j<3; j++)
            {
                if (current[j].equals(keyword))
                {
                    counter++;
                }
            }
        }

        System.out.println("matches = "+ counter);
    }
}