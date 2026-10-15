interface Switchable
{
    void on();
    void off();
    boolean isOn();

    default void toggle()
    {
        if (isOn())
        {
            off();
        }
        else
        {
            on();
        }
    }
}

class Fan implements Switchable
{
    boolean state = false;

    public void on()
    {
        state = true;
        System.out.println("device turned on");
    }

    public void off()
    {
        state = false;
        System.out.println("device turned off");
    }

    public boolean isOn()
    {
        return state;
    }
}

class Light implements Switchable
{
    boolean state = false;

    public void on()
    {
        state = true;
        System.out.println("device turned on");
    }

    public void off()
    {
        state = false;
        System.out.println("device turned off");
    }

    public boolean isOn()
    {
        return state;
    }
}

@FunctionalInterface
interface TimeRule
{
    boolean canSwitchOn(Switchable device, int hour);
}

public class remoteControl
{
    public static void main(String[] args)
    {
        Switchable[] devices = {
            new Fan(),
            new Light()
        };

        for (Switchable device : devices)
        {
            device.toggle();
        }

        TimeRule dayRule = new TimeRule()
        {
            public boolean canSwitchOn(Switchable device, int hour)
            {
                return hour >= 6 && hour <= 22;
            }
        };

        TimeRule nightRule = (device, hour) -> hour >= 22 || hour <= 6;

        int currentHour = 20;

        System.out.println("can switch on (day rule) : " + dayRule.canSwitchOn(devices[0], currentHour));
        System.out.println("can switch on (night rule) : " + nightRule.canSwitchOn(devices[0], currentHour));
    }
}