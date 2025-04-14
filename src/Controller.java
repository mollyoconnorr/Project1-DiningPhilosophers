/**
 * Controller class manages the simulation of the Dining Philosophers problem.
 * It initializes the philosophers and chopsticks, starts and stops the simulation,
 * and interacts with the GUI to update the philosopher states.
 * <p>
 * Author: Murat Guzelocak and Molly O'Conor
 * Date: 04/13/2025
 */
public class Controller {
    // Number of philosophers participating in the simulation
    private final int numPhilosophers;

    // Array of philosopher names
    private final String[] philosopherNames;

    // Reference to the GUI that displays the state of the simulation
    private final DiningPhilosophersGUI gui;

    // Array of Philosopher objects representing the philosophers
    private Philosopher[] philosophers;

    // Array of Chopstick objects representing the chopsticks used by the philosophers
    private Chopstick[] chopsticks;

    // Flag to indicate whether the simulation is running or not
    private volatile boolean isRunning = false;

    /**
     * Constructor to initialize the Controller, GUI, and philosopher names.
     * Sets up the GUI and passes the current Controller instance to the GUI.
     *
     * @param numPhilosophers  the number of philosophers participating in the simulation
     * @param philosopherNames an array containing the names of the philosophers
     */
    public Controller(int numPhilosophers, String[] philosopherNames) {
        this.numPhilosophers = numPhilosophers;
        this.philosopherNames = philosopherNames;
        this.gui = new DiningPhilosophersGUI(numPhilosophers, philosopherNames);
        this.gui.setController(this);
        this.gui.createAndShowGUI();
    }

    /**
     * Initializes the simulation by creating the chopsticks and philosophers.
     * Each philosopher is assigned a left and right chopstick.
     * The philosophers' initial state is set to "stopped".
     */
    public void initialStart() {
        if (isRunning) return;
        isRunning = true;

        chopsticks = new Chopstick[numPhilosophers];
        philosophers = new Philosopher[numPhilosophers];

        for (int i = 0; i < numPhilosophers; i++) {
            chopsticks[i] = new Chopstick();
        }

        for (int i = 0; i < numPhilosophers; i++) {
            Chopstick left = chopsticks[i];
            Chopstick right = chopsticks[(i + 1) % numPhilosophers];
            philosophers[i] = new Philosopher(i, left, right, gui, philosopherNames[i]);
            gui.updateState(i, "stopped");
        }
    }

    /**
     * Starts the simulation by starting each philosopher's thread.
     */
    public void startSimulation() {
        for (int i = 0; i < numPhilosophers; i++) {
            philosophers[i].start();
        }
    }

    /**
     * Stops the simulation by stopping each philosopher's thread.
     * The isRunning flag is set to false to indicate that the simulation is no longer running.
     */
    public void stopSimulation() {
        if (!isRunning) return;
        isRunning = false;
        for (Philosopher p : philosophers) {
            p.stopPhilosopher();
        }
    }
}


