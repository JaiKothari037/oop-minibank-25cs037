import java.util.Scanner;

public class Driver
{
    public static void main(String[] args)
    {
        Card cards[] = new Card[5];
        Scanner sc = new Scanner(System.in);

        String rank = "";
        String suit = "";

        int i = 0;
        while (i<5)
        {

            System.out.print("enter rank :");
            rank = sc.next();

            System.out.print("enter suit :");
            suit = sc.next();
            
            Card a = new Card(rank,suit);

            if (i == 0)
            {
                cards[i] = a;
                System.out.println("card added");
                i++;
                continue;
            }

            int check = 0;
            for (int j = 0; j < i; j++)
            {
                if (cards[j].equals(a))
                {
                    System.out.println("duplicate found" + a.toString());
                    check = 1;
                    break;
                }
            }

            if (check == 0)
            {
                System.out.println("card added");
                cards[i] = a;
                i++;
            }
        }
    }
}