class Box<T> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}

class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

public class GenericContainersDemo {
    public static <T> T getFirstElement(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        Box<String> stringBox = new Box<String>();
        stringBox.set("Java Generics");
        String strVal = stringBox.get();

        Box<Integer> intBox = new Box<Integer>();
        intBox.set(100);
        Integer intVal = intBox.get();

        System.out.println("Box<String> value: " + strVal);
        System.out.println("Box<Integer> value: " + intVal);

        Pair<String, Integer> student = new Pair<String, Integer>("Riya", 42);
        Pair<Double, Boolean> status = new Pair<Double, Boolean>(9.5, true);

        System.out.println("Student Pair: " + student);
        System.out.println("Status Pair: " + status);

        String name = student.getFirst();
        Integer roll = student.getSecond();
        System.out.println("Extracted without casts: Name = " + name + ", Roll = " + roll);

        String[] names = {"Neel", "Aman", "Riya"};
        Integer[] scores = {95, 88, 76};

        String firstName = getFirstElement(names);
        Integer firstScore = getFirstElement(scores);

        System.out.println("First name in array: " + firstName);
        System.out.println("First score in array: " + firstScore);
    }
}