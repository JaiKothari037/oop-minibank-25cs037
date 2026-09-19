interface Switchable
{
    void on() {}
    void off() {}
    void isOn() {}

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

    public void isOn()
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
}

@FunctonalInterface
interface timeRule
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
        }

        for (Switchable device : devices)
        {
            device.toggle();
        }
    }
}