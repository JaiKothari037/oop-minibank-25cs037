import java.util.concurrent.atomic.AtomicLong;

public class ThreadArraySum {

    static final int ARRAY_SIZE = 10_000_000;
    static final int THREAD_COUNT = 4;
    static int[] numbers = new int[ARRAY_SIZE];

    static long unsafeSum = 0;
    static AtomicLong atomicSum = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < ARRAY_SIZE; i++) {
            numbers[i] = 1;
        }

        runUnsafeSum();
        runSynchronizedSum();
        runAtomicSum();
        runPartialSums();
    }

    static void runUnsafeSum() throws InterruptedException {
        unsafeSum = 0;
        Thread[] threads = new Thread[THREAD_COUNT];
        int chunkSize = ARRAY_SIZE / THREAD_COUNT;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * chunkSize;
            int end = (i == THREAD_COUNT - 1) ? ARRAY_SIZE : start + chunkSize;

            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    unsafeSum += numbers[j];
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("--- Unsafe Approach ---");
        System.out.println("Result: " + unsafeSum);
        System.out.println("Time taken: " + (endTime - startTime) + " ms\n");
    }

    static void runSynchronizedSum() throws InterruptedException {
        unsafeSum = 0;
        Object lock = new Object();
        Thread[] threads = new Thread[THREAD_COUNT];
        int chunkSize = ARRAY_SIZE / THREAD_COUNT;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * chunkSize;
            int end = (i == THREAD_COUNT - 1) ? ARRAY_SIZE : start + chunkSize;

            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    synchronized (lock) {
                        unsafeSum += numbers[j];
                    }
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("--- Fix Method 1: Synchronized Block ---");
        System.out.println("Result: " + unsafeSum);
        System.out.println("Time taken: " + (endTime - startTime) + " ms\n");
    }

    static void runAtomicSum() throws InterruptedException {
        atomicSum.set(0);
        Thread[] threads = new Thread[THREAD_COUNT];
        int chunkSize = ARRAY_SIZE / THREAD_COUNT;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * chunkSize;
            int end = (i == THREAD_COUNT - 1) ? ARRAY_SIZE : start + chunkSize;

            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    atomicSum.addAndGet(numbers[j]);
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("--- Fix Method 2: AtomicLong ---");
        System.out.println("Result: " + atomicSum.get());
        System.out.println("Time taken: " + (endTime - startTime) + " ms\n");
    }

    static void runPartialSums() throws InterruptedException {
        long[] partialSums = new long[THREAD_COUNT];
        Thread[] threads = new Thread[THREAD_COUNT];
        int chunkSize = ARRAY_SIZE / THREAD_COUNT;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int threadId = i;
            int start = threadId * chunkSize;
            int end = (threadId == THREAD_COUNT - 1) ? ARRAY_SIZE : start + chunkSize;

            threads[threadId] = new Thread(() -> {
                long localSum = 0;
                for (int j = start; j < end; j++) {
                    localSum += numbers[j];
                }
                partialSums[threadId] = localSum;
            });
            threads[threadId].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long totalSum = 0;
        for (long partial : partialSums) {
            totalSum += partial;
        }

        long endTime = System.currentTimeMillis();

        System.out.println("--- Alternative Fix: Local Partial Sums ---");
        System.out.println("Result: " + totalSum);
        System.out.println("Time taken: " + (endTime - startTime) + " ms\n");
    }
}