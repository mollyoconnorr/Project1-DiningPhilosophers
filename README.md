# Dining Philosophers

This project provides a multithreaded solution to the Dining Philosophers problem — a classic synchronization and concurrency challenge. The problem involves five philosophers sitting around a table, each needing two chopsticks to eat. Since the chopsticks are shared resources between neighbors, proper synchronization is essential to prevent deadlocks. In our implementation, each philosopher is modeled as a separate thread that continuously cycles through thinking, attempting to pick up chopsticks, eating, and then putting the chopsticks down.

To simulate realistic behavior, each philosopher randomly selects an action — either thinking or eating — and sleeps for a random duration to mimic the time spent on that activity. The most challenging part of this project was solving the deadlock issue that arose when philosophers couldn’t acquire both chopsticks. We addressed this by implementing a checkpoint in the pickUp() method: if a philosopher successfully acquires both chopsticks, they proceed to eat; otherwise, they release any chopstick they may have picked up and return to thinking. This retry mechanism, using pickUp() and putDown() methods, ensures fair and deadlock-free resource allocation. To make the process visual and intuitive, we also developed a GUI that displays each philosopher’s current state in real time.


