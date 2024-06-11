package es.upm.pproject.sokoban;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.LinkedList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.model.Nivel;
import es.upm.pproject.sokoban.model.Tablero;

class NivelTest {
    @Test
    @DisplayName("Test Constructor: nivel no existe") 
    void test1(){
        assertThrows(FileNotFoundException.class, 
            () -> new Nivel(100));
    }

    @Test
    @DisplayName("Test Constructor: nivel mal construido") 
    void test2(){
        assertThrows(es.upm.pproject.sokoban.exceptions.IncorrectLevelException.class, 
            () -> new Nivel(400));
    }

    @Test
    @DisplayName("Test Constructor: datos correctos") 
    void test3() throws Exception{
        Nivel nivel = new Nivel(1);
        assertNotNull(nivel);
    }

    @Test
    @DisplayName("Test Constructor vacío: datos correctos") 
    void test4(){
        Nivel nivel = new Nivel();
        assertNotNull(nivel);
        assertEquals(new LinkedList<>(), nivel.getEstadosAnteriores());
    }

    @Test
    @DisplayName("Test getPuntuacionRelativa") 
    void test5() throws Exception{
        Nivel nivel = new Nivel(1);
        assertEquals(0, nivel.getPuntuacionRelativa());
    }

    @Test
    @DisplayName("Test getTablero") 
    void test6() throws Exception{
        Nivel nivel = new Nivel(1);
        assertEquals(new Tablero("./niveles/level_1.txt").toString(),
            nivel.getTablero().toString());
    }

    @Test
    @DisplayName("Test getEstadosAnteriores") 
    void test7() throws Exception{
        Nivel nivel = new Nivel(1);
        assertNotNull(nivel.getEstadosAnteriores());
        assertTrue(nivel.getEstadosAnteriores() instanceof Deque<?>);
        assertEquals(1, nivel.getEstadosAnteriores().size());
    }

    @Test
    @DisplayName("Test deshacer") 
    void test8() throws Exception{
        Nivel nivel = new Nivel(1);
        nivel.deshacer();
        assertEquals(new Tablero("./niveles/level_1.txt").toString(),
            nivel.getTablero().toString());
        assertEquals(0, nivel.getEstadosAnteriores().size());
    }

    @Test
    @DisplayName("Test incremetarPuntuacionRelativa") 
    void test9() throws Exception{
        Nivel nivel = new Nivel(1);
        assertEquals(0, nivel.getPuntuacionRelativa());
        nivel.incremetarPuntuacionRelativa();
        assertEquals(1, nivel.getPuntuacionRelativa());
    } 
}