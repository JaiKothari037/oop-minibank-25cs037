import org.junit.Test;
import static org.junit.Assert.*;

public class StudentServiceTest
{
    @Test
    public void testAddIncreasesCount()
    {
        StudentService service = new StudentService();
        assertEquals(0, service.count());

        service.add("Riya", 85);
        assertEquals(1, service.count());

        service.add("Aman", 90);
        assertEquals(2, service.count());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMarksThrowsException()
    {
        StudentService service = new StudentService();
        service.add("Riya", 150);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyNameThrowsException()
    {
        StudentService service = new StudentService();
        service.add("", 85);
    }
}