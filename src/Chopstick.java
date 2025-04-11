import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {
    private final ReentrantLock lock = new ReentrantLock();

    public boolean pickUp() {
        return lock.tryLock(); // non-blocking
    }

    public void putDown() {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
