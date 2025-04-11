public class DiningPhilosophers {
    public static void main(String[] args) {
        int numPhilosophers = 5;
        Philosopher[] philosophers = new Philosopher[numPhilosophers];
        Chopstick[] chopsticks = new Chopstick[numPhilosophers];

        for (int i = 0; i < numPhilosophers; i++) {
            chopsticks[i] = new Chopstick();
        }

        for (int i = 0; i < numPhilosophers; i++) {
            Chopstick left = chopsticks[i];
            Chopstick right = chopsticks[(i + 1) % numPhilosophers];

            // Avoid deadlock by having one philosopher pick up right then left
            if (i == numPhilosophers - 1) {
                philosophers[i] = new Philosopher(i, right, left);
            } else {
                philosophers[i] = new Philosopher(i, left, right);
            }

            philosophers[i].start();
        }

        // Let them run for a bit
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop philosophers
        for (Philosopher p : philosophers) {
            p.stopPhilosopher();
        }
    }
}