import java.util.Scanner;

public class Driver
{
    public static void main(String[] args) {

        int num;
        int den;
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i<3; i++)
        {
            System.err.print("enter numerator : ");
            num = sc.nextInt();
            System.err.print("enter denominator : ");
            den = sc.nextInt();
            Fractions a = new Fractions(num, den);
            System.out.println(a.toString());
        }
        
    }
}