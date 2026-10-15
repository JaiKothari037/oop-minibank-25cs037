import java.util.Scanner;

class DivideByZeroException extends Exception
{
    DivideByZeroException(String message)
    {
        super(message);
    }
}

public class guarded_calculator
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        boolean success = false;

        while (!success)
        {
            try
            {
                System.out.print("enter first number: ");
                double num1 = Double.parseDouble(sc.next());

                System.out.print("enter operator (+, -, *, /): ");
                String op = sc.next();

                System.out.print("enter second number: ");
                double num2 = Double.parseDouble(sc.next());

                if (op.equals("/") && num2 == 0)
                {
                    throw new DivideByZeroException("cannot divide by zero");
                }

                double result = switch (op) {
                    case "+" -> num1 + num2;
                    case "-" -> num1 - num2;
                    case "*" -> num1 * num2;
                    case "/" -> num1 / num2;
                    default -> throw new IllegalArgumentException("invalid operator: " + op);
                };

                System.out.println("result: " + result);
                success = true;
            }
            catch (NumberFormatException e)
            {
                System.out.println("error: invalid number format");
            }
            catch (DivideByZeroException e)
            {
                System.out.println("error: " + e.getMessage());
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("error: " + e.getMessage());
            }
            finally
            {
                System.out.println("attempt logged");
            }
        }
    }
}