package es.upm.pproject.sokoban;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestsTablero {
    @Test
    @DisplayName("Test Constructor: nivel no existe") 
    void test1(){
        assertThrows(FileNotFoundException.class, 
            () -> new Tablero("./niveles/level_2000.txt"));
    }

    @Test
    @DisplayName("Test Constructor: nivel mal construido") 
    void test2(){
        assertThrows(es.upm.pproject.sokoban.exceptions.IncorrectLevelException.class, 
            () -> new Tablero("./niveles/level_400.txt"));
    }

    @Test
    @DisplayName("Test Constructor: datos correctos") 
    void test3() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_1.txt");
        assertNotNull(tablero);
    }

    @Test
    @DisplayName("Test comprobar fin: datos correctos") 
    void test4() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_401.txt");
        tablero.mover('r');
        assertTrue(tablero.comprobarFin());
    }

    @Test
    @DisplayName("Test setMatriz y getMatriz: datos correctos") 
    void test5() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_1.txt");
        tablero.setMatriz(new char[0][0]);
        assertTrue(tablero.getMatriz().length == 0);
    }

    @Test
    @DisplayName("Test getNombreMatriz: datos correctos") 
    void test6() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_401.txt");
        assertEquals("Custom level", tablero.getNombreNivel());
    }
}