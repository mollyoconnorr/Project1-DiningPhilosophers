/**
 * Main class for running the Dining Philosophers simulation.
 * It initializes the list of philosopher names, shuffles them,
 * and starts the simulation for a specified number of philosophers.
 * The simulation runs until the user stops it.
 * <p>
 * Author: Murat Guzelocak and Molly O'Conor
 * Date: 04/13/2025
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Array of philosopher names
        String[] philosopherNames = {
                "Ted Wendt", "Guz", "Socrates", "Plato", "Aristotle", "Descartes", "Nate Williams",
                "Molly", "Shaun Scott"
        };

        // Shuffle philosopher names for randomness
        List<String> shuffledNames = Arrays.asList(philosopherNames.clone());
        Collections.shuffle(shuffledNames);

        // Convert shuffled names back into an array
        String[] shuffledNamesArray = shuffledNames.toArray(new String[0]);

        // Number of philosophers to simulate (can be changed)
        int numPhilosophers = 5;

        // Initialize the Controller with the shuffled philosopher names and number
        Controller controller = new Controller(numPhilosophers, shuffledNamesArray);

        // Start the simulation
        controller.initialStart();
    }
}
