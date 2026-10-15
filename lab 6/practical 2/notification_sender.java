@FunctionalInterface
interface Notifier
{
    void send(String message);
}

interface Urgent
{
}

class UrgentNotifier implements Notifier, Urgent
{
    private final Notifier notifier;

    public UrgentNotifier(Notifier notifier)
    {
        this.notifier = notifier;
    }

    @Override
    public void send(String message)
    {
        notifier.send(message);
    }
}

public class notification_sender
{
    public static void main(String[] args)
    {
        Notifier emailSender = message -> System.out.println("email: " + message);
        Notifier smsSender = new UrgentNotifier(message -> System.out.println("sms: " + message));

        Notifier[] notifiers = { emailSender, smsSender };

        String message = "system alert";

        for (Notifier n : notifiers)
        {
            n.send(message);
            if (n instanceof Urgent)
            {
                n.send(message);
            }
        }
    }
}