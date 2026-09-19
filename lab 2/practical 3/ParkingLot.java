public class ParkingLot
{
    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;

    public ParkingLot(int twoCap, int fourCap)
    {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
    }

    public void give_revenue()
    {
        System.out.println(revenue);
    }

    public void park(String type)
    {
        if (type.equals("two"))
        {
            if (twoWheelers < twoCap)
            {
                twoWheelers++;
                revenue += 20;
                System.out.println("parked");
            }
            else
            {
                System.out.println("FULL");
            }
        }

        if (type.equals("four"))
        {
            if (fourWheelers < fourCap)
            {
                fourWheelers++;
                revenue += 40;
                System.out.println("parked");
            }
            else
            {
                System.out.println("FULL");
            }
        }
    }

    public void leave(String type)
    {
        if (type.equals("two"))
        {
            if (twoWheelers>0)
            {
                twoWheelers--;
                System.out.println("two wheeler left");
            }
            else
            {
                System.out.println("no two wheelers");
            }
        }
        if (type.equals("four"))
        {
            if (fourWheelers>0)
            {
                fourWheelers--;
                System.out.println("four wheeler left");
            }
            else
            {
                System.out.println("no four wheelers");
            }
        }
    }

    public static void main(String[] args)
    {
        ParkingLot p = new ParkingLot(3,3);

        p.park("two");
        p.park("two");
        p.park("two");
        p.park("two");
        p.leave("two");
        p.leave("two");
        p.leave("two");
        p.leave("two");
        p.give_revenue();
    }
}