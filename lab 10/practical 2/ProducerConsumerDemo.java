class SharedBuffer
{
    private int[] buffer;
    private int capacity;
    private int count;
    private int head;
    private int tail;

    public SharedBuffer(int size)
    {
        capacity = size;
        buffer = new int[capacity];
        count = 0;
        head = 0;
        tail = 0;
    }

    public synchronized void produce(int item) throws InterruptedException
    {
        while (count == capacity)
        {
            wait();
        }

        buffer[tail] = item;
        tail = (tail + 1) % capacity;
        count++;

        System.out.println("Produced: " + item);
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException
    {
        while (count == 0)
        {
            wait();
        }

        int item = buffer[head];
        head = (head + 1) % capacity;
        count--;

        System.out.println("Consumed: " + item);
        notifyAll();
        return item;
    }
}

public class ProducerConsumerDemo
{
    public static void main(String[] args)
    {
        SharedBuffer sharedBuffer = new SharedBuffer(3);
        int totalItems = 10;

        Thread producer = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    for (int i = 1; i <= totalItems; i++)
                    {
                        sharedBuffer.produce(i);
                        Thread.sleep(100);
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    for (int i = 1; i <= totalItems; i++)
                    {
                        sharedBuffer.consume();
                        Thread.sleep(200);
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}