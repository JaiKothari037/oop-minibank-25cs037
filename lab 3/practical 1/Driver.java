public class Driver
{
    public static void main(String[] args)
    {
        Point[] p = new Point[5];

        p[0] = new Point(2,3);
        p[1] = new Point(4,5);
        p[2] = new Point(5,6);
        p[3] = new Point(3,4);
        p[4] = new Point(2,3);

        int distinct = 5;

        for (int i = 0; i< 5; i++)
        {
            for (int j = i+1; j<5; j++)
            {
                if (p[i].equals(p[j]))
                {
                    distinct -= 1;
                }
            }
        }


        System.out.println("distinct points - " + distinct);
    }
}