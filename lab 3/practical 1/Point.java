import java.util.Objects;

public class Point
{
    private int x;
    private int y;

    Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object a)
    {
        if ((((Point)a).x == this.x) && (((Point)a).y == this.y))
        {
            return true;
        }

        return false;
    }

    public int hashCode()
    {
        return Objects.hash(x,y);
    }


}