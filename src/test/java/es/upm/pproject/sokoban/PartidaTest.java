package es.upm.pproject.sokoban;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.controller.Controlador;
import es.upm.pproject.sokoban.exceptions.IncorrectLevelException;
import es.upm.pproject.sokoban.model.Nivel;
import es.upm.pproject.sokoban.model.Partida;
import es.upm.pproject.sokoban.view.Vista;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

class PartidaTest {
    
    private Partida partida;
    private Nivel nivel;
    private Vista vista;
    private Controlador controlador;
    
    @BeforeEach
    void setUp() throws Exception{
        nivel = new Nivel(1);
        partida = new Partida(nivel, 0, 1);
        vista = new Vista();
        controlador = new Controlador(vista, partida);
        partida.setControlador(controlador);
    }
    
    @Test
    @DisplayName("Test ConstructorVacio")
    void test1() {
        Partida partida2 = new Partida();
        assertNotNull(partida2);
    }

    @Test
    @DisplayName("Test getControlador")
    void test2() {
        assertEquals(controlador, partida.getControlador());
    }
    
    @Test
    @DisplayName("Test setControlador")
    void test3() {
        Vista newVista = new Vista();
        Partida newPartida = new Partida(nivel, 0, 1);
        Controlador newControlador = new Controlador(newVista, newPartida);
        partida.setControlador(newControlador);
        assertEquals(newControlador, partida.getControlador());
    }
    
    @Test
    @DisplayName("Test getNivel")
    void test4() {
        assertEquals(nivel, partida.getNivel());
    }
    
    @Test
    @DisplayName("Test setNivel")
    void test5() throws Exception{
        Nivel newNivel = new Nivel(1);
        partida.setNivel(newNivel);
        assertEquals(newNivel, partida.getNivel());
    }
    
    @Test
    @DisplayName("Test getPuntuacionAbsoluta")
    void test6() {
        assertEquals(0, partida.getPuntuacionAbsoluta());
    }
    
    @Test
    @DisplayName("Test setPuntuacionAbsoluta")
    void test7() {
        partida.setPuntuacionAbsoluta(100);
        assertEquals(100, partida.getPuntuacionAbsoluta());
    }
    
    @Test
    @DisplayName("Test getNumeroNivel")
    void test8() {
        assertEquals(1, partida.getNumeroNivel());
    }
    
    @Test
    @DisplayName("Test setNumeroNivel")
    void test9() {
        partida.setNumeroNivel(2);
        assertEquals(2, partida.getNumeroNivel());
    }
    
    @Test
    @DisplayName("Test constructor con controlador")
    void test10() {
        Partida newPartida = new Partida(nivel, 0, 1, controlador);
        assertEquals(nivel, newPartida.getNivel());
        assertEquals(0, newPartida.getPuntuacionAbsoluta());
        assertEquals(1, newPartida.getNumeroNivel());
        assertEquals(controlador, newPartida.getControlador());
    }
    
    @Test
    @DisplayName("Test constructor sin controlador")
    void test11() {
        Partida newPartida = new Partida(nivel, 0, 1);
        assertEquals(nivel, newPartida.getNivel());
        assertEquals(0, newPartida.getPuntuacionAbsoluta());
        assertEquals(1, newPartida.getNumeroNivel());
        assertNull(newPartida.getControlador());
    }
    
    @Test
    @DisplayName("Test siguienteNivel")
    void test12() {
        assertTrue(partida.siguienteNivel());
        assertEquals(2, partida.getNumeroNivel());
    }
    
    @Test
    @DisplayName("Test mover sin bloqueo")
    void test13() throws InterruptedException {
        partida.mover('u');
        assertEquals(1, partida.getPuntuacionAbsoluta());
        assertEquals(1, partida.getNivel().getPuntuacionRelativa());
    }

    @Test
    @DisplayName("Test mover con bloqueo")
    void test14() throws InterruptedException {
        partida.mover('l');
        assertEquals(0, partida.getPuntuacionAbsoluta());
        assertEquals(0, partida.getNivel().getPuntuacionRelativa());
    }
    
    @Test
    @DisplayName("Test siguienteNivel con fichero no existente")
    void test15() throws FileNotFoundException, es.upm.pproject.sokoban.exceptions.IncorrectLevelException {
        Partida partida2 = new Partida(new Nivel(3), 0, 3, controlador);
        assertFalse(partida2.siguienteNivel());
    }

    @Test
    @DisplayName("Test siguienteNivel con nivel mal construido")
    void test16() throws InterruptedException, es.upm.pproject.sokoban.exceptions.IncorrectLevelException {
        Partida partida2 = new Partida();
        partida2.setNumeroNivel(399);
        assertFalse(partida2.siguienteNivel());
    }
    
    @Test
    @DisplayName("Test deshacer sin estado anterior")
    void test17() throws FileNotFoundException, IncorrectLevelException {
        assertEquals(new Nivel(1).getTablero().toString(), partida.getNivel().getTablero().toString());
        partida.deshacer();
        assertEquals(new Nivel(1).getTablero().toString(), partida.getNivel().getTablero().toString());
    }

    @Test
    @DisplayName("Test deshacer con estado anterior")
    void test18() throws InterruptedException {
        partida.mover('u');
        partida.mover('u');
        partida.deshacer();
        assertEquals(1, partida.getPuntuacionAbsoluta());
        assertEquals(1, partida.getNivel().getPuntuacionRelativa());
    }

    @Test
    @DisplayName("Test reiniciar correcto")
    void test19() throws InterruptedException {
        partida.mover('u');
        assertTrue(partida.reiniciar());
        assertEquals(0, partida.getPuntuacionAbsoluta());
        assertEquals(0, partida.getNivel().getPuntuacionRelativa());
    }

    @Test
    @DisplayName("Test reiniciar con nivel mal construido")
    void test20() {
        Partida partida2 = new Partida();
        partida2.setNumeroNivel(400);
        partida2.setNivel(new Nivel());
        assertFalse(partida2.reiniciar());
    }

    @Test
    @DisplayName("Test reiniciar con fichero inexistente")
    void test21() {
        Partida partida2 = new Partida();
        partida2.setNumeroNivel(5);
        partida2.setNivel(new Nivel());
        assertFalse(partida2.reiniciar());
    }

    @Test
    @DisplayName("Test mover con final de nivel")
    void test22() throws FileNotFoundException, IncorrectLevelException, InterruptedException {
        Partida partida2 = new Partida(new Nivel(401), 0, 401, controlador);
        partida2.mover('r');
        assertEquals(new Nivel(402).getTablero().toString(), partida2.getNivel().getTablero().toString());
    }
}
