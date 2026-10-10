class MyThread extends Thread
{
    public static int counter = 0;
    @Override
    public synchronized void run()
    {
        for (int i = 0; i< 10000; i++)
        {
            counter++;
        }
    }
}

public class CounterRace
{
    public static void main(String[] args) throws InterruptedException
    {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        System.out.println(MyThread.counter);
    }
}