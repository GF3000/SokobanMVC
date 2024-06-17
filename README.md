# sokoban

This project contains 

## Contents: 

- **Maven Configuration**: The project is built and managed using Maven. Configuration settings are located in `pom.xml`.
- **SLF4J Configuration**: Logging is handled by SLF4J, with configuration settings in `src/main/resources/log4j.properties`.
- **JAXB Example**: Demonstrates how to write Java objects into XML files, located in the package `es.upm.pproject.examples.jaxb`.
- **JUnit5 Annotations Examples**: Showcases the use of JUnit5 annotations for testing, found in the package `es.upm.pproject.examples.testing`.

## Project Structure
This project is organized into several key directories, each serving a specific purpose in the development and execution of the application. Below is an overview of the project's folder structure and a brief description of each directory's contents.

- **deliverables**: 
- **log**:
- **src/main/java/es/upm/pproject/sokoban**
    - **controller**: Contains classes responsible for handling the game's logic and interactions between the model and view. 
        - `Controlador.java`: Implements the game's control logic, managing game states and user actions. 
        - `ControladorInterface.java`: Defines the interface for the game controller, outlining the methods that must be implemented. 
    - **exceptions**: Custom exception class.
        - `IncorrectLevelException.java`: Thrown when an invalid level is encountered or fails to load. 
    - **model**: Holds the core classes that represent the game's data model. 
        - `Nivel.java`: Represents a single level in the game. 
        - `NivelInterface.java`: Defines the interface for a game level.
        - `Partida.java`: Represents a game session, containing the current level, score and game state. 
        - `PartidaInterface.java`: Outlines the methods for managing a game session. 
        - `Tablero.java`: Represents the game board, including the positions of walls, boxes, and the player.
        - `TableroInterface.java`: Specifies the functionalities required for a game board.
    - **view**: Contains classes related to the graphical user interface of the game. 
        - **imagenes**: Stores image files used in the game's GUI, such as icons and sprites. 
        - `Vista.java`: Manages the display of the game, including rendering the board and handling user inputs. 
    - `Sokoban.java`: The main class that launches the game application. 
- **src/test/java/es/upm/pproject/sokoban**: Contains the unit tests specific for the methods of each class.
    - `ControladorTest.java`
    - `NivelTest.java`
    - `PartidaTest.java`
    - `TableroTest.java`
- **target**: 




#### src/main/java/es/upm/pproject/sokoban/exceptions

#### src/main/java/es/upm/pproject/sokoban/model

#### src/main/java/es/upm/pproject/sokoban/view


### src/main/resources



### src/test



## Introduction
This repository contains the code of a Java program to manage the students that
are matriculated in the different courses offered by a university.

## Interfaces

### NivelInterface
This interface represents a course and contains different methods related to managing students enrolled in the course.

#### Methods

- **getPuntuacionRelativa()**
  - Description: This method is called to get the relative score.
  - Returns: the relative score.

- **getTablero()**
  - Description: This method is called to get the tablero.
  - Returns: the tablero.

- **getEstadosAnteriores()**
  - Description: This method is called to get all the previous states.
  - Returns: all the previous states.

- **pushInEstadosAnteriores(char[][] matriz)**
  - Description: Add an array to the stack of previous states.
  - Parameters: 
    - *matriz* - The array that is to be added.

- **deshacer()**
  - Description: Undo the last move and return to a previous state.
  - Returns: true if there was a previous state.

- **incremetarPuntuacionRelativa()**
  - Description: Increase relative score.
  
### PartidaInterface
This interface represents a course and contains different methods related to managing students enrolled in the course.

#### Methods

- **getControlador()**
  - Description: This method is called to get the controller.
  - Returns: the controller.


- **setControlador(ControladorInterface controlador)**
  - Description: This method is called to set the controller.
  - Parameters: 
    - *controlador* - The controller that is to be setted.


- **getNivel()**
  - Description: This method is called to get a level.
  - Returns: the level.


- **setNivel(Nivel nivel)**
  - Description: This method is called to set the level.
  - Parameters: 
    - *nivel* - The level that is to be setted.


- **getPuntuacionAbsoluta()**
  - Description: This method is called to get the absolute puntuacion.
  - Returns: the absolute puntuacion.

- **setPuntuacionAbsoluta(int puntuacionAbsoluta)**
  - Description: This method sets the absolute puntuaction.
  - Parameters: 
    - *puntuacionAbsoluta* - The puntuaction that is to be setted.

- **getNumeroNivel()**
  - Description: This method is called to get the level number.
  - Returns: an integer representing the level.

- **setNumeroNivel(int numeroNivel)**
  - Description: This method is called to set the level number.
  - Parameters: 
    - *numeroNivel* - The level number that is to be setted.

- **siguienteNivel()**
  - Description: This method is called to move to the next level.
  - Returns: true if the level was increased successfully.


- **mover(char direccion)**
  - Description: moves the character to the direction specified.
  - Parameters: 
    - *direccion* - a character that is used to specify the direction in which the character is going to be moved.

  
- **reiniciar()**
  - Description: This method is called to restart the level.


### TableroInterface
This interface represents a course and contains different methods related to managing students enrolled in the course.

#### Methods

- **nivelCorrecto()**
  - Description: This method is called to check whether the level is correct.
  - Returns: a boolean value indicating whether the level is correct or not.
  

- **mover()**
  - Description: This method is called to execute a movement.
  - Returns: a boolean value indicating whether the movement could be executed successfully.
  

- **comprobarFin()**
  - Description: This method is called to check whether the end has been reached.
  - Returns: a boolean value indicating whether the end has been reached or not.
  

- **setMatriz(char[][] matriz)**
  - Description: This method sets the matriz of the tablero.
  - Parameters: 
    - *matriz* -  It is the matriz that is to be setted. 

- **getMatriz()**
  - Description: This method is called to get the matriz.
  - Returns: It gets the matriz.
  

- **getNombreNivel()**
  - Description: This method is called to get the name of the level.
  - Returns: It gets the name of the level.
  

- **toString()**
  - Description: This method is called to get the string representation of the table.
  - Returns: A string representation of the table.
  
