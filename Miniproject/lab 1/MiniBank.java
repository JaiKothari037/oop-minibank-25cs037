import java.util.Scanner;

public class MiniBank
{
    public record BankInfo(String name, String branch) {}
    public enum MenuOption{ OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT}

    public static void main(String[] args) {
        BankInfo bank = new BankInfo("jai", "hdfc ahm");
        System.out.println(bank.toString());
        Scanner sc = new Scanner(System.in);
        int chosen = 0;
        
        do {
            System.out.println("MENU");
            System.out.println("1. OPEN_ACCOUNT");
            System.out.println("2. DEPOSIT");
            System.out.println("3. WITHDRAW");
            System.out.println("4. TRANSFER");
            System.out.println("5. EXIT");
            System.out.print("enter a number : ");

            chosen = sc.nextInt();

            String placeholder = switch (chosen) 
            {
                case 1 -> "openaccount to be implemented in later lab";
                case 2 -> "deposit to be implemented in later lab";
                case 3 -> "withdraw to be implemented in later lab";
                case 4 -> "transfer to be implemented in later lab";
                case 5 -> "exit to be implemented in later lab";
                default -> "wrong input";
            };

        } while (chosen != 5);

        System.out.println("goodbye");
    }
}