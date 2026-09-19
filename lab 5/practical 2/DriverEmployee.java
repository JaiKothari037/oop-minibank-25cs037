abstract class Employee
{
    private String name;
    private int id;

    public Employee(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    abstract public int monthlySalary();

    public void getdetails()
    {
        System.out.print(id + name);
    }
}

class FullTime extends Employee
{
    public FullTime(String name, int id)
    {
        super(name, id);
    }

    public int monthlySalary()
    {
        return 5000;
    }
}

class PartTime extends Employee
{
    int hours;
    int rate;

    public PartTime(String name, int id, int hours, int rate)
    {
        super(name, id);
        this.hours = hours;
        this.rate = rate;
    }

    public int monthlySalary()
    {
        return hours * rate;
    }
}

class Intern extends Employee
{
    public Intern(String name, int id)
    {
        super(name, id);
    }

    public int monthlySalary()
    {
        return 2000;
    }
}

public class DriverEmployee
{
    public static void main(String[] args)
    {
        Employee[] employees = {
            new FullTime("maahil", 1),
            new PartTime("jai", 2, 24, 500),
            new Intern("shubh", 3),
        };

        int totalpayroll = 0;
        for (int i = 0; i<3; i++)
        {
            int p = employees[i].monthlySalary();
            totalpayroll += p;
            employees[i].getdetails();
            System.out.println(p);
        }
    }
}