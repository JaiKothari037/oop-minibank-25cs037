import java.util.Scanner;

public class TollBooth
{
    public record Vehicle(String number, String type) {}

    public static void main(String[] args) {
        {
            int totaltoll = 0;
            int bike = 0;
            int car = 0;
            int truck = 0;
            
            Scanner sc = new Scanner(System.in);
            String type;
            String number;

            while(true)
            {
                System.out.print("enter vehicle bike / car / truck (enter done to stop) : ");
                type = sc.next();

                if (type.equals("done"))
                {
                    break;
                }

                System.out.print("enter vehicle number : ");
                number = sc.next();

                Vehicle v = new Vehicle(number, type);

                switch(type)
                {
                    case "bike" -> {
                        bike++;
                        totaltoll += 20;
                }

                    case "car" -> {
                        car++;
                        totaltoll += 50;
                }

                    case "truck" -> {
                        truck++;
                        totaltoll += 150;
                }
                }
                

            }

            System.out.println("total toll : " + totaltoll);

            if ((bike > car) && (bike > truck))
            {
                System.out.println("most frequent : bike");
            }
            else if ((car > bike) && (car > truck))
            {
                System.out.println("most frequent : car");
            }
            else
            {
                System.out.println("most frequent : truck");
            }
        }
    }    
}