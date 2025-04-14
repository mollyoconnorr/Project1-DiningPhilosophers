/**
 * DiningPhilosophersGUI class creates and manages the visual representation
 * for the Dining Philosophers simulation. It displays the philosophers' states
 * (thinking, hungry, eating) and the status of chopsticks (available or held).
 * It also provides buttons to start and stop the simulation, and updates the
 * philosopher states and chopstick statuses in real-time.
 * <p>
 * ChatGPT was used to help with JFrame visual appearance (alignment, fonts, etc.)
 * <p>
 * Author: Murat Guzelocak and Molly O'Conor
 * Date: 04/13/2025
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * File: DinningPhilosophersGUI
 * Date: 04/12/2025
 * Description:
 * The {@code DiningPhilosophersGUI} class provides a graphical interface to visualize
 * the Dining Philosophers simulation. It displays each philosopher's name, current state
 * (e.g., thinking, hungry, eating), and the status of each chopstick (available or held).
 *
 * <p>
 * This GUI includes control buttons to start and stop the simulation, and updates the
 * visual representation in real-time based on the simulation’s progress. Each philosopher
 * and chopstick has a dedicated label whose appearance (text and color) dynamically reflects
 * their state, enhancing the user's understanding of synchronization behavior.
 *
 * <p>
 * The GUI layout is constructed using Java Swing components such as {@link JFrame},
 * {@link JPanel}, {@link JLabel}, and {@link JButton}, and uses a {@link Controller}
 * object to communicate with the simulation logic.
 *
 * <p>
 * This implementation serves both educational and demonstrative purposes,
 * helping users understand how threads and resource sharing work in concurrent systems.
 *
 * <p>
 * <b>Note:</b> ChatGPT was used to assist with the design and layout of the graphical interface.
 *
 * @author Murat Guzelocak and Molly O'Conor
 */
public class DiningPhilosophersGUI extends JFrame {
    /** Arrays to store the labels for philosopher names, states, and chopstick statuses */
    private final JLabel[] philosopherLabels;
    private final JLabel[] chopstickLabels;

    /** Array to store philosopher names */
    private final String[] philosopherNames;

    /** Control buttons for starting and stopping the simulation */
    private final JButton startButton;
    private final JButton stopButton;

    /** Reference to the Controller to interact with the simulation logic */
    private Controller controller;

    /**
     * Constructor to initialize the GUI with a given number of philosophers and their names.
     * Sets up the layout, header, table for displaying philosopher and chopstick states,
     * and the control buttons for starting and stopping the simulation.
     *
     * @param numPhilosophers the number of philosophers
     * @param philosopherNames an array containing the names of the philosophers
     */
    public DiningPhilosophersGUI(int numPhilosophers, String[] philosopherNames) {
        this.philosopherNames = philosopherNames;
        philosopherLabels = new JLabel[numPhilosophers];
        chopstickLabels = new JLabel[numPhilosophers];

        setTitle("Dining Philosophers");
        setLayout(new BorderLayout());
        setSize(1500, 500); // Bigger size

        // Header panel
        JPanel headerPanel = new JPanel(new GridLayout(1, 3));
        add(headerPanel, BorderLayout.NORTH);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // top, left, bottom, right
        JLabel nameHeader = new JLabel("Philosopher Name", SwingConstants.CENTER);
        nameHeader.setFont(new Font("SansSerif", Font.BOLD, 16));
        headerPanel.add(nameHeader);

        JLabel stateHeader = new JLabel("State", SwingConstants.CENTER);
        stateHeader.setFont(new Font("SansSerif", Font.BOLD, 16));
        headerPanel.add(stateHeader);

        JLabel chopstickHeader = new JLabel("Chopstick Status", SwingConstants.CENTER);
        chopstickHeader.setFont(new Font("SansSerif", Font.BOLD, 16));
        headerPanel.add(chopstickHeader);

        // Main table panel
        JPanel tablePanel = new JPanel(new GridLayout(numPhilosophers, 3));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < numPhilosophers; i++) {
            JLabel nameLabel = new JLabel(philosopherNames[i], SwingConstants.CENTER);
            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            tablePanel.add(nameLabel);

            philosopherLabels[i] = new JLabel("Thinking", SwingConstants.CENTER);
            philosopherLabels[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            tablePanel.add(philosopherLabels[i]);

            chopstickLabels[i] = new JLabel("Available", SwingConstants.CENTER);
            chopstickLabels[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            tablePanel.add(chopstickLabels[i]);
        }
        add(tablePanel, BorderLayout.CENTER);

        // Panel for control buttons
        JPanel controlPanel = new JPanel();
        startButton = new JButton("Start Simulation");
        stopButton = new JButton("Stop Simulation");

        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Set up button actions
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.initialStart();
                    controller.startSimulation();
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.stopSimulation();
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Sets the Controller to interact with the simulation.
     *
     * @param controller the Controller instance
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Updates the state label for a given philosopher with the new state.
     * The state color changes depending on the state (eating, hungry, or other).
     *
     * @param philosopherId the ID of the philosopher
     * @param state the new state of the philosopher
     */
    public void updateState(int philosopherId, String state) {
        SwingUtilities.invokeLater(() -> {
            philosopherLabels[philosopherId].setText(state);
            if (state.toLowerCase().startsWith("eating")) {
                philosopherLabels[philosopherId].setForeground(new Color(0, 153, 0)); // Green for eating
            } else if (state.toLowerCase().startsWith("hungry")) {
                philosopherLabels[philosopherId].setForeground(Color.ORANGE); // Orange for hungry
            } else {
                philosopherLabels[philosopherId].setForeground(Color.BLACK); // Default color
            }
        });
    }

    /**
     * Updates the chopstick label for a given chopstick with the new state.
     * The color changes based on whether the chopstick is held or available.
     *
     * @param chopstickId the ID of the chopstick
     * @param state the new state of the chopstick (Held or Available)
     */
    public void updateChopstickState(int chopstickId, String state) {
        SwingUtilities.invokeLater(() -> {
            chopstickLabels[chopstickId].setText(state);
            if (state.equalsIgnoreCase("Held")) {
                chopstickLabels[chopstickId].setForeground(new Color(204, 0, 0));
            } else {
                chopstickLabels[chopstickId].setForeground(Color.BLACK);
            }
        });
    }

    /**
     * Makes the GUI visible by setting the frame to visible.
     */
    public void createAndShowGUI() {
        setVisible(true);
    }
}



