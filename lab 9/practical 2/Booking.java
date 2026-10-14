// class Task implements Runnable
// {
//     public void run()
//     {
//         if (Booking.seatsLeft > 0)
//         {
//             try {
//                 Thread.sleep(50);
//             } catch (Exception e) {}
//             Booking.seatsLeft--;
//         }
//     }
// }

// public class Booking
// {
//     public static int seatsLeft = 5;
//     public static void main(String[] args) throws InterruptedException 
//     {
//         Thread thread1 = new Thread(new Task());
//         Thread thread2 = new Thread(new Task());
//         Thread thread3 = new Thread(new Task());
//         Thread thread4 = new Thread(new Task());
//         Thread thread5 = new Thread(new Task());
//         Thread thread6 = new Thread(new Task());
//         Thread thread7 = new Thread(new Task());
//         Thread thread8 = new Thread(new Task());
//         Thread thread9 = new Thread(new Task());
//         Thread thread10 = new Thread(new Task());

//         thread1.start();
//         thread2.start();
//         thread3.start();
//         thread4.start();
//         thread5.start();
//         thread6.start();
//         thread7.start();
//         thread8.start();
//         thread9.start();
//         thread10.start();
//         thread1.join();
//         thread2.join();
//         thread3.join();
//         thread4.join();
//         thread5.join();
//         thread6.join();
//         thread7.join();
//         thread8.join();
//         thread9.join();
//         thread10.join();

//         System.out.println(seatsLeft);

//     }
// }

class Task implements Runnable
{
    public void run()
    {
        Booking.book();
    }
}

public class Booking
{
    public static int seatsLeft = 5;

    public synchronized static void book()
    {
        if (Booking.seatsLeft > 0)
        {
            try {
                Thread.sleep(50);
            } catch (Exception e) {}
            seatsLeft--;
        }
    }
    
    public static void main(String[] args) throws InterruptedException 
    {
        Thread thread1 = new Thread(new Task());
        Thread thread2 = new Thread(new Task());
        Thread thread3 = new Thread(new Task());
        Thread thread4 = new Thread(new Task());
        Thread thread5 = new Thread(new Task());
        Thread thread6 = new Thread(new Task());
        Thread thread7 = new Thread(new Task());
        Thread thread8 = new Thread(new Task());
        Thread thread9 = new Thread(new Task());
        Thread thread10 = new Thread(new Task());

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        thread7.join();
        thread8.join();
        thread9.join();
        thread10.join();

        System.out.println(seatsLeft);

    }
}