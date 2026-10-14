import java.util.ArrayList;

public class StudentService
{
    private ArrayList<Student> students = new ArrayList<>();

    public void add (String name, int marks)
    {
        if (name == null || name.isBlank())
        {
            throw new IllegalArgumentException("name cannot be empty");
        }
        
        if (marks < 0 || marks > 100)
        {
            throw new IllegalArgumentException("marks must be in range");
        }

        students.add(new Student(name, marks));

    }

    public int count()
    {
        return students.size();
    }

    public ArrayList<Student> getAll()
    {
        return students;
    }
}