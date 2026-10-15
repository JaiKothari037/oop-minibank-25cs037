package app;

import engine.DiscountRule;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiscountApp
{
    public static void main(String[] args)
    {
        List<Double> prices = new ArrayList<>();
        prices.add(100.0);
        prices.add(250.0);
        prices.add(50.0);

        Scanner sc = new Scanner(System.in);
        System.out.println("select discount rule:");
        System.out.println("1. 10% off");
        System.out.println("2. flat 20 off");
        System.out.print("choice: ");

        int choice = sc.nextInt();

        DiscountRule rule = switch (choice) {
            case 1 -> price -> price * 0.90;
            case 2 -> price -> Math.max(0, price - 20);
            default -> price -> price;
        };

        System.out.println("discounted prices:");
        for (double price : prices)
        {
            System.out.println("original: " + price + " -> discounted: " + rule.apply(price));
        }
    }
}