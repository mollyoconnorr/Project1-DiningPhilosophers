public class Philosopher extends Thread {
    private final int id;
    private final Chopstick leftChopstick;
    private final Chopstick rightChopstick;
    private volatile boolean running = true;

    public Philosopher(int id, Chopstick left, Chopstick right) {
        this.id = id;
        this.leftChopstick = left;
        this.rightChopstick = right;
    }

    @Override
    public void run() {
        try {
            while (running) {
                think();
                if (pickUpChopsticks()) {
                    eat();
                    putDownChopsticks();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep((int)(Math.random() * 1000)); // simulate thinking
    }

    private boolean pickUpChopsticks() {
        boolean leftAcquired = leftChopstick.pickUp();
        boolean rightAcquired = rightChopstick.pickUp();

        if (leftAcquired && rightAcquired) {
            System.out.println("Philosopher " + id + " picked up both chopsticks.");
            return true;
        } else {
            if (leftAcquired) leftChopstick.putDown();
            if (rightAcquired) rightChopstick.putDown();
            System.out.println("Philosopher " + id + " tried to pick up chopsticks but couldn't get both.");
            return false;
        }
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep((int)(Math.random() * 1000)); // simulate eating
    }

    private void putDownChopsticks() {
        leftChopstick.putDown();
        rightChopstick.putDown();
        System.out.println("Philosopher " + id + " put down both chopsticks.");
    }

    public void stopPhilosopher() {
        running = false;
    }
}
