import java.util.Scanner;

public  class  smart_thermostat
{
    private String location;
    private int temperature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount  = 0;

    public smart_thermostat(String location, int startTemp)
    {
        this.location = location;
        if ((startTemp > MIN) && (startTemp < MAX))
        {
            temperature = startTemp;
        }
        else
        {
            temperature = 22;
        }
        activeCount++;
    }

    public smart_thermostat(String location)
    {
        this.location = location;
        temperature = 22;
        activeCount++;
    }

    public void raise()
    {
        if (temperature < MAX)
        {
            temperature++;
        }
        else
        {
            System.out.println("already at maximum : 30 degrees");
        }
    }

    public void lower()
    {
        if (temperature > MIN)
        {
            temperature--;
        }
        else
        {
            System.out.println("already at minimum : 16 degrees");
        }
    }

    public void getTemperature()
    {
        System.out.println("current temperature is : " + temperature);
    }

    public static void getActiveCount()
    {
        System.out.println("current count is : " + activeCount);
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter location : ");
        String loc = sc.next();
        System.out.print("enter starting temperature : ");
        int strtmp = sc.nextInt();
        smart_thermostat thermo1 = new smart_thermostat(loc, strtmp);
        smart_thermostat thermo2 = new smart_thermostat(loc);

        for (int i = 0; i < 10; i++)
        {
            thermo1.raise();
            thermo1.getTemperature();
        }

        thermo1.getActiveCount();
    }
}
