import java.util.concurrent.Semaphore;
import java.util.Random;

public class DiningPhilosophers {
    private static final int NUM_PHILOSOPHERS = 5;

    // Chopsticks: one semaphore for each
    private static final Semaphore[] chopsticks = new Semaphore[NUM_PHILOSOPHERS];

    // Waiter: allows max 4 philosophers to try to eat at once
    private static final Semaphore waiter = new Semaphore(NUM_PHILOSOPHERS - 1);

    public static void main(String[] args) {
        // Initialize chopsticks
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            chopsticks[i] = new Semaphore(1);
        }

        // Create and start philosopher threads
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            new Philosopher(i).start();
        }
    }

    static class Philosopher extends Thread {
        private final int index;
        private final int leftChopstick;
        private final int rightChopstick;
        private final Random random = new Random();
        private static int philosophersDone = 0;
        private static final Object lock = new Object();

        public Philosopher(int index) {
            this.index = index;
            leftChopstick = index;
            rightChopstick = (index + 1) % NUM_PHILOSOPHERS;
        }

        public void run() {
            try {
                // Thinking
                System.out.println("Philosopher " + index + " is thinking.");
                Thread.sleep(random.nextInt(2000) + 1000);

                // Ask waiter for permission to try eating
                System.out.println("Philosopher " + index + " is hungry.");
                waiter.acquire();

                // Pick up chopsticks
                chopsticks[leftChopstick].acquire();
                chopsticks[rightChopstick].acquire();

                // Eat
                System.out.println("Philosopher " + index + " is eating.");
                Thread.sleep(random.nextInt(1000) + 1000);

                // Put down chopsticks
                chopsticks[leftChopstick].release();
                chopsticks[rightChopstick].release();
                waiter.release();

                // Mark done
                synchronized (lock) {
                    philosophersDone++;
                    System.out.println("Philosopher " + index + " finished eating.");

                    // If all philosophers ate once, exit
                    if (philosophersDone == NUM_PHILOSOPHERS) {
                        System.out.println("All philosophers have eaten once. Exiting program.");
                        System.exit(0);  // Force exit
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Philosopher " + index + " was interrupted.");
            }
        }
    }
}