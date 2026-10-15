import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String name;
    double cgpa;
    transient String password; // Will NOT be serialized

    public Student(String name, double cgpa, String password) {
        this.name = name;
        this.cgpa = cgpa;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', cgpa=" + cgpa + ", password='" + password + "'}";
    }
}

public class SaveLoadDemo {
    public static void main(String[] args) {
        String filename = "students.ser";
        Student[] originalStudents = {
            new Student("Riya", 8.8, "riyaPass123"),
            new Student("Aman", 7.5, "amanPass456"),
            new Student("Diya", 9.2, "diyaPass789")
        };

        // 1. Serialize the array of objects to a file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(originalStudents);
            System.out.println("Array of objects successfully saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving objects: " + e.getMessage());
        }

        // 2. Deserialize back into fresh objects
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Student[] loadedStudents = (Student[]) in.readObject();
            System.out.println("\nLoaded Objects:");
            for (Student s : loadedStudents) {
                System.out.println("  " + s);
            }
            System.out.println("\nNote: 'password' fields are restored as 'null' because they were marked transient.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading objects: " + e.getMessage());
        }
    }
}