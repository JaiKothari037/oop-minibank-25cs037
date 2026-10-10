class Booking {

    static int seatsLeft = 5;
    static int successfulBookings = 0;

    // Without synchronization
    static void book() {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(100); // makes the race condition easier to see
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            seatsLeft--;
            successfulBookings++;
            System.out.println(Thread.currentThread().getName() + " booked a seat");
        }
    }

    // With synchronization
    static synchronized void synchronizedBook() {
        if (seatsLeft > 0) {
            seatsLeft--;
            successfulBookings++;
            System.out.println(Thread.currentThread().getName() + " booked a seat");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // Test without synchronization
        System.out.println("Without synchronization:");

        seatsLeft = 5;
        successfulBookings = 0;

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> book(), "Thread-" + (i + 1));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Successful bookings: " + successfulBookings);
        System.out.println("Seats left: " + seatsLeft);

        // Test with synchronization
        System.out.println("\nWith synchronization:");

        seatsLeft = 5;
        successfulBookings = 0;

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> synchronizedBook(), "Thread-" + (i + 1));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Successful bookings: " + successfulBookings);
        System.out.println("Seats left: " + seatsLeft);
    }
}