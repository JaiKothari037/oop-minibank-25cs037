import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner
{
    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++)
        {
            int taskId = i;
            executor.submit(new Runnable()
            {
                public void run()
                {
                    System.out.println("Task " + taskId + " executed by thread: " + Thread.currentThread().getName());
                    try
                    {
                        Thread.sleep(200);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }

        executor.shutdown();

        try
        {
            if (executor.awaitTermination(5, TimeUnit.SECONDS))
            {
                System.out.println("All tasks completed successfully.");
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}