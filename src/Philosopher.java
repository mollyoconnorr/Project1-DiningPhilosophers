import javax.swing.*;

/**
 * This class models a philosopher in the Dining Philosophers problem.
 * Each philosopher thinks, picks up chopsticks, eats, and then puts down the chopsticks.
 * The simulation ensures proper synchronization between philosophers to avoid deadlocks.
 * <p>
 * Author: Murat Guzelocak and Molly O'Conor
 * Date: 04/13/2025
 */

public class Philosopher extends Thread {

    /** Array of foods that philosophers will eat */
    private static final String[] FOODS = {
            "Tammy's pizza", "hamburger", "flavorless chicken", "fries", "bagel", "rice and corn", "rice and corn", "rice and corn"
    };

    /** Array of thoughts for philosophers */
    private static final String[] THOUGHTS = {
            "magnetic tape", "the meaning of life", "how to pick up chopsticks faster", "what's for dinner",
            "ethics of eating", "how to avoid starvation", "fluffy pink bunny slippers"
    };

    /** Unique ID for the philosopher */
    private final int id;

    /** Reference to the left chopstick */
    private final Chopstick leftChopstick;

    /** Reference to the right chopstick */
    private final Chopstick rightChopstick;

    /** GUI reference for updating the state */
    private final DiningPhilosophersGUI gui;

    /** Philosopher's name */
    private final String name;
    /** Flag to control the philosopher's activity */
    private volatile boolean running = true;

    /** Keep track of times philosopher has eaten */
    private int eatCount;

    /**
     * Constructor to initialize the philosopher.
     *
     * @param id    The philosopher's ID
     * @param left  The left chopstick
     * @param right The right chopstick
     * @param gui   Reference to the GUI for state updates
     * @param name  The name of the philosopher
     */
    public Philosopher(int id, Chopstick left, Chopstick right, DiningPhilosophersGUI gui, String name, int eatCount) {
        this.id = id;
        this.leftChopstick = left;
        this.rightChopstick = right;
        this.gui = gui;
        this.name = name;
        this.eatCount = 0;
    }

    /**
     * The main logic for the philosopher's actions. Thinks, picks up chopsticks, eats, and puts them down.
     */
    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            ((JLabel) ((JPanel) gui.getContentPane().getComponent(1)).getComponent(id * 4 + 3)).setText("Eat Count: " + eatCount);
        });
        while (running) {
            try {
                think();
                if (!running) {
                    break;
                }
                if (pickUpChopsticks()) {
                    eat();
                    putDownChopsticks();
                }
            } catch (InterruptedException e) {
                running = false;
                break;
            }
        }
        leftChopstick.putDown();
        rightChopstick.putDown();
        gui.updateChopstickState(id, "Available");
        gui.updateState(id, "stopped");
    }

    /**
     * The philosopher thinks about something.
     * This is simulated with a random thought and a random sleep time.
     *
     * @throws InterruptedException If the thread is interrupted during sleep.
     */
    private void think() throws InterruptedException {
        String thought = THOUGHTS[(int) (Math.random() * THOUGHTS.length)];
        System.out.println(name + " is thinking about " + thought + ".");
        gui.updateState(id, "Thinking about " + thought);
        Thread.sleep((int) (Math.random() * 5000)); // Simulate thinking
    }

    /**
     * The philosopher tries to pick up both chopsticks.
     * If unsuccessful, they release any acquired chopsticks and retry.
     * By releasing any acquired chopsticks when unsuccessful, we avoid deadlock.
     *
     * @return true if both chopsticks are successfully picked up, false otherwise.
     */
    private boolean pickUpChopsticks() {
        gui.updateState(id, "Hungry");
        boolean leftAcquired = false;
        boolean rightAcquired = false;
        boolean hasPrintedRetryMessage = false;


        while (!(leftAcquired && rightAcquired) && running) {
            if (!leftAcquired) {
                leftAcquired = leftChopstick.pickUp();
            }
            if (!rightAcquired) {
                rightAcquired = rightChopstick.pickUp();
            }
            if (!(leftAcquired && rightAcquired)) {
                // Release any chopstick acquired so far and retry
                if (leftAcquired) {
                    leftChopstick.putDown();
                    leftAcquired = false;
                }
                if (rightAcquired) {
                    rightChopstick.putDown();
                    rightAcquired = false;
                }
                if (!hasPrintedRetryMessage) {
                    System.out.println(name + " couldn't acquire both chopsticks, retrying.");
                    hasPrintedRetryMessage = true;
                }
                gui.updateState(id, "hungry");
            }
        }
        System.out.println(name + " picked up both chopsticks.");
        gui.updateChopstickState(id, "Held");
        return true;
    }

    /**
     * The philosopher eats their food. This is simulated with a random food item and sleep time.
     *
     * @throws InterruptedException If the thread is interrupted during sleep.
     */
    private void eat() throws InterruptedException {
        String foodItem = FOODS[(int) (Math.random() * FOODS.length)];
        gui.updateState(id, "Eating " + foodItem);
        eatCount ++;
        System.out.println(name + " is eating.");
        Thread.sleep((int) (Math.random() * 2500)); // Simulate eating
        // ChatGPT generated this GUI assistance line
        SwingUtilities.invokeLater(() -> {
            ((JLabel) ((JPanel) gui.getContentPane().getComponent(1)).getComponent(id * 4 + 3)).setText("Eat Count: " + eatCount);
        });
    }

    /**
     * The philosopher puts down both chopsticks.
     */
    private void putDownChopsticks() {
        leftChopstick.putDown();
        rightChopstick.putDown();
        System.out.println(name + " put down both chopsticks.");
        gui.updateChopstickState(id, "Available");
        gui.repaint(); // Ensure GUI reflects this immediately
    }

    /**
     * Stop the philosopher from running.
     */
    public void stopPhilosopher() {
        running = false;
        this.interrupt();
    }
}

