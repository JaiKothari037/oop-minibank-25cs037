import java.util.EmptyStackException;

class ArrayStack<T> {
    private Object[] data;
    private int top;
    private int capacity;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.top = -1;
    }

    public void push(T item) {
        if (top == capacity - 1) {
            throw new IllegalStateException("Stack Overflow");
        }
        top++;
        data[top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = (T) data[top];
        data[top] = null;
        top--;
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
}

public class GenericStackDemo {
    public static void main(String[] args) {
        ArrayStack<String> stringStack = new ArrayStack<String>(3);
        stringStack.push("First");
        stringStack.push("Second");
        stringStack.push("Third");

        System.out.println("String Stack size: " + stringStack.size());
        System.out.println("Top element: " + stringStack.peek());
        while (!stringStack.isEmpty()) {
            System.out.println("Popped: " + stringStack.pop());
        }

        try {
            stringStack.pop();
        } catch (EmptyStackException e) {
            System.out.println("Handled empty stack pop for String stack gracefully.");
        }

        ArrayStack<Integer> intStack = new ArrayStack<Integer>(5);
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);

        System.out.println("\nInteger Stack size: " + intStack.size());
        System.out.println("Top element: " + intStack.peek());
        while (!intStack.isEmpty()) {
            System.out.println("Popped: " + intStack.pop());
        }

        try {
            intStack.peek();
        } catch (EmptyStackException e) {
            System.out.println("Handled empty stack peek for Integer stack gracefully.");
        }
    }
}