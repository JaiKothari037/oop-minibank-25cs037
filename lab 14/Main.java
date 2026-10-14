import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        StudentService service = new StudentService();
        Scanner sc = new Scanner(System.in);

        while(true)
        {
            System.out.println("1. add student 2. list students 3. exit");

            int choice = sc.nextInt();

            if (choice == 1)
            {
                System.out.print("name: ");
                String name = sc.next();
                System.out.print("marks: ");
                int marks = sc.nextInt();

                try{
                    service.add(name, marks);
                    System.out.println("added");
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println("error: " + e.getMessage());
                }
                
            }

            else if (choice == 2)
            {
                for (Student s : service.getAll())
                {
                    System.out.println(s);
                }
                System.out.println("total: " + service.count());
            }

            else
            {
                break;
            }
        }
    }
}