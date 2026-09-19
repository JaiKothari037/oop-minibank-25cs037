
abstract class Shape
{
    public abstract float area();
}

class Circle extends Shape
{
    private int radius;

    public Circle(int radius)
    {
        this.radius = radius;
    }       

    @Override
    public float area()
    {
        return (3.14f)*(radius*radius);
    }
}

class Rectangle extends Shape
{
    private int length;
    private int breadth;

    public Rectangle(int length, int breadth)
    {
        this.length = length;
        this.breadth = breadth;
    }

    @Override
    public float area()
    {
        return length * breadth;
    }
}

class Triangle extends Shape
{
    private int side1;
    private int side2;
    private int side3;

    public Triangle(int side1, int side2, int side3)
    {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    @Override
    public float area()
    {
        float s = (side1+side2+side3)/2.0f;
        //System.out.println(s);
        return (float) Math.sqrt((s * (s - side1) * (s - side2) * (s - side3)));
    }
}

public class DriverShape
{
    public static void main(String[] args)
    {
        Shape[] shapes = new Shape[5];
        shapes[0] = new Circle(5);
        shapes[1] = new Triangle(2,7,6);
        shapes[2] = new Rectangle(4,8);
        shapes[3] = new Circle(6);
        shapes[4] = new Rectangle(2,3);

        float maxArea = 0;
        float total = 0;
        for (int i = 0; i < 5; i++)
        {
            float a = shapes[i].area();
            System.out.println(a);
            if (a>maxArea)
            {
                maxArea = a;
            }
            total += a;
        }

        System.out.println("maximum area - " + maxArea);
        System.out.println("running total - " + total);


    }
}