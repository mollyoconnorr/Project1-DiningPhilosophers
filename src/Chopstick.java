import java.util.concurrent.locks.ReentrantLock;

/**
 * Chopstick class represents a chopstick in the Dining Philosophers problem.
 * It uses a ReentrantLock to manage access and ensure mutual exclusion.
 *
 * Author: Murat Can Guzelocak and Molly O'Conor
 * Date: 04/13/2025
 */
public class Chopstick {
    private final ReentrantLock lock;

    /**
     * Constructor to create a chopstick with a fair ReentrantLock.
     */
    public Chopstick() {
        // Fair lock to prevent starvation: threads acquire the lock in FIFO order
        this.lock = new ReentrantLock(true);
    }

    /**
     * Attempts to pick up the chopstick.
     * This method returns true if the lock was successfully acquired,
     * false otherwise.
     *
     * @return true if the chopstick was picked up, false if it's already in use
     */
    public boolean pickUp() {
        // Try acquiring the lock immediately without blocking
        return lock.tryLock();
    }

    /**
     * Puts down the chopstick, making it available to others.
     */
    public void putDown() {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}