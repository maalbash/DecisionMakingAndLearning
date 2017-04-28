# Decision Making

This project is HW number 3 for CSC584. The basic for the environment and basic movement have been reused from previous assignments and from the course project.

The player wanders the environment using a decision tree to decide what to do. The monster uses a behavior tree to try to shoot the player or reach it. After using a learning algorithm, the Monster starts using a decision tree spit out by the algorithm.

The classes structure is as follows:
* **engine** package contains two classes, **Engine** which runs the whole show and calls other classes and methods, And **Logger** which writes the results to a file.
* **movement** package contains all the classes related to different kinds of movement needed in this project. Classes are **Align**, **Arrive**, **Flee**, **Seek**, and **Wander**. It also contains two classes that define the kinematic and steering variables.
* **objects** contains all the objects used in the project. The hierarchy of objects is as follows: **AbstractObject** is the parent for **GameObject** which is a parent for **Player** and **Monster**. **Bullet** and **Breadcrumbs** are different because they don't share the same attribute with Player and Monster.
* **Monster** and **Player** both have their respective decision trees implemented inside their classes.
* **Utility** contains all the helper functions and the different constants used throughout the project.
* **Behavior** contains the classes for different nodes used in the assignment.
* **Learning** contains the algorithm used for getting the Information gain which is used for the final decision tree.