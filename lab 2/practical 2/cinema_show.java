public class cinema_show
{
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;

    public cinema_show(String title, int capacity)
    {
        this.title = title;
        this.capacity = capacity;
        seatsAvailable = capacity;
    }

    public cinema_show(String title)
    {
        this(title, 100);
    }

    public boolean book(int n)
    {
        if (n < seatsAvailable)
        {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        }
        return false;
    }

    public void cancel (int n)
    {
        if ((seatsAvailable+n) <= capacity)
        {
            seatsAvailable += n;
            totalBooked -= n;
        }
    }

    public void getSeatsAvailable()
    {
        System.out.println("total seats available are - " + seatsAvailable);
    }

    public static void getTotalBooked()
    {
        System.out.println("total booked till now are - " + totalBooked);
    }

    public static void main(String[] args) {
        cinema_show a = new cinema_show("resident evil", 100);
        a.book(4);
        a.getSeatsAvailable();
        a.book(52);
        a.getSeatsAvailable();
        a.book(100);
        a.getSeatsAvailable();
        a.cancel(30);
        a.getSeatsAvailable();
        a.book(20);
        a.getSeatsAvailable();
        a.getTotalBooked();
    }
}