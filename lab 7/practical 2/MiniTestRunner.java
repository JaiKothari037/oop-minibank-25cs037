import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run
{
}

class TestSuite
{
    @Run
    public void testOne()
    {
        System.out.println("testOne executed");
    }

    public void helperMethod()
    {
        System.out.println("helperMethod executed");
    }

    @Run
    public void testTwo()
    {
        System.out.println("testTwo executed");
    }
}

public class MiniTestRunner
{
    public static void main(String[] args)
    {
        TestSuite suite = new TestSuite();
        Method[] methods = suite.getClass().getDeclaredMethods();
        int count = 0;

        for (Method m : methods)
        {
            if (m.isAnnotationPresent(Run.class))
            {
                try
                {
                    m.setAccessible(true);
                    m.invoke(suite);
                    count++;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("total tests run: " + count);
    }
}