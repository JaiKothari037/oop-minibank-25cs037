    import java.util.Scanner;

public class vending_machine
{
    enum coin {ONE, TWO, FIVE, TEN} 

    public static void main(String[] args)
    {
        final int PRICE = 15;
        int total = 0;
        Scanner sc = new Scanner(System.in); 
    
        System.out.println("snack price: " + PRICE);
        System.out.println("Insert coins (ONE, TWO , FIVE, TEN)");
        while(total < PRICE)
        {
            System.out.print("Coin: ");
            String input = sc.next().toUpperCase();

            coin Coin;

            try
            {
                Coin = coin.valueOf(input);
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(" Not a valid coin, try again.");
                continue;
            }

            int value = switch(Coin)
            {
                case ONE -> 1;
                case TWO -> 2;
                case FIVE -> 5;
                case TEN -> 10;
            };

            total += value;

            System.out.println("Inserted so far: " + total);
        }

        int change = total - PRICE;
        System.out.println("change : " + change);
    }
}