import java.util.Scanner;

public class Driver
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i<5; i++)
        {
            System.out.print("password : ");
            String input = sc.next();

            PasswordChecker pw = new PasswordChecker(input);            
        }
    }
}