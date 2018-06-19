# SmartFridge

An example implementation of SmartFridge interface

## Assumptions

1. Since the description mentions 'containers' for storing items, it is assumed that itemUUID is a container ID. If it were to be treated as a unique identifier for a product, then having 'fill factor' along with it when adding an item doesn't make much sense. Also, container information won't be available then. It is therefore assumed that 'handleItemAdded' method provides details on an item type being added to a container, and the resulting fill factor after the addition.
2. With the above assumption, the application lets the same item type be stored on multiple containers. When a new request comes with the same item type and container id, the application just updates the fill factor for that container with the one provided.
3. A sample class ItemQuantity is used to depict result of getItems() method, which contains item type and fill factor. This is not a critical class for the application, there can be several other ways in which this information may be sent back, including a simple delimited string. A class representation is however more extensible.
4. A private class 'Item' is used to depict an item. It's private since the information it stores is not of concern outside of the main class.
5. The logic to send notifications isn't implemented, but it's conceivable that in real world, notifications are sent on add and remove method calls.

## Running the tests
The project is created as a maven project, in Eclipse. To download the application and run the test cases (created using JUnit), follow the below instructions:
1. Download/Clone the repository (https://github.com/aalapk/SmartFridge) and extract in any folder
2. Using command line, navigate to the base folder (SmartFridge-master/SmartFridge)
3. Run 'mvn compile' to compile the code
4. Run 'mvn test' to run and validate tests

## Dependencies

1. JDK 1.8 or higher (for compiling)
2. Maven (needs to be in the path). It takes care of other dependencies, e.g. junit
3. JAVA_HOME variable is set to the home directory of JDK
