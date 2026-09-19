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
}