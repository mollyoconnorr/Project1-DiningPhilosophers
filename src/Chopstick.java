/**
 * Chopstick class represents a chopstick in the Dining Philosophers problem.
 * It provides synchronized methods to simulate picking up and putting down a chopstick.
 * This class ensures that only one philosopher can hold a chopstick at a time.
 * <p>
 * Author: Murat Can Guzelocak and Molly O'Conor
 * Date: 04/13/2025
 */
public class Chopstick {
    private boolean inUse = false;

    /**
     * Attempts to pick up the chopstick.
     * This method returns true if the chopstick was successfully picked up,
     * and false if it is already in use by another philosopher.
     *
     * @return true if the chopstick was picked up, false if it is already in use
     */
    public synchronized boolean pickUp() {
        if (!inUse) {
            inUse = true;
            return true;
        }
        return false;
    }

    /**
     * Puts down the chopstick, making it available for other philosophers to use.
     * This method is synchronized to ensure thread safety when changing the status of the chopstick.
     */
    public synchronized void putDown() {
        inUse = false;
    }
}