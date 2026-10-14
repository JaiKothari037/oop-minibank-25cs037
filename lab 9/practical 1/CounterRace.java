// class CounterThread extends Thread
// {
//     @Override
//     public void run()
//     {
//         for (int i = 0; i<10000; i++)
//         {
//             CounterRace.count++;
//         }
//     }
// }

// public class CounterRace
// {
//    public static int count = 0;

//    public static void main(String[] args) throws InterruptedException
//    {
//         CounterThread a = new CounterThread();
//         Thread thread1 = new Thread(a);
//         CounterThread b = new CounterThread();
//         Thread thread2 = new Thread(b);

//         thread1.start();
//         thread2.start();
//         thread1.join();
//         thread2.join();

//         System.out.println(count);
//    } 
// }

class CounterThread extends Thread
{
    @Override
    public void run()
    {
        for (int i = 0; i<10000; i++)
        {
            CounterRace.increment();
        }
    }
}

public class CounterRace
{
   public static int count = 0;

   public synchronized static void increment()
   {
        count++;
   }

   public static void main(String[] args) throws InterruptedException
   {
        CounterThread a = new CounterThread();
        Thread thread1 = new Thread(a);
        CounterThread b = new CounterThread();
        Thread thread2 = new Thread(b);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(count);
   } 
}