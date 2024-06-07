package es.upm.pproject.sokoban;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Stack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.Nivel;
import es.upm.pproject.sokoban.Controlador;
import es.upm.pproject.sokoban.Partida;
import es.upm.pproject.sokoban.Vista;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;




import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class PartidaTest {
    
    private Partida partida;
    private Nivel nivel;
    private Vista vista;
    private Controlador controlador;
    
    @BeforeEach
    public void setUp() throws Exception{
        nivel = new Nivel(1);
        partida = new Partida(nivel, 0, 1);
        vista = new Vista();
        controlador = new Controlador(vista, partida);
        partida.setControlador(controlador);
    }
    
    @Test
    public void testGetControlador() {
        assertEquals(controlador, partida.getControlador());
    }
    
    @Test
    public void testSetControlador() {
        Vista newVista = new Vista();
        Partida newPartida = new Partida(nivel, 0, 1);
        Controlador newControlador = new Controlador(newVista, newPartida);
        partida.setControlador(newControlador);
        assertEquals(newControlador, partida.getControlador());
    }
    
    @Test
    public void testGetNivel() {
        assertEquals(nivel, partida.getNivel());
    }
    
    @Test
    public void testSetNivel() throws Exception{

        Nivel newNivel = new Nivel(1);
        partida.setNivel(newNivel);
        assertEquals(newNivel, partida.getNivel());
    }
    
    @Test
    public void testGetPuntuacionAbsoluta() {
        assertEquals(0, partida.getPuntuacionAbsoluta());
    }
    
    @Test
    public void testSetPuntuacionAbsoluta() {
        partida.setPuntuacionAbsoluta(100);
        assertEquals(100, partida.getPuntuacionAbsoluta());
    }
    
    @Test
    public void testGetNumeroNivel() {
        assertEquals(1, partida.getNumeroNivel());
    }
    
    @Test
    public void testSetNumeroNivel() {
        partida.setNumeroNivel(2);
        assertEquals(2, partida.getNumeroNivel());
    }
    
    @Test
    public void testConstructorWithControlador() {
        Partida newPartida = new Partida(nivel, 0, 1, controlador);
        assertEquals(nivel, newPartida.getNivel());
        assertEquals(0, newPartida.getPuntuacionAbsoluta());
        assertEquals(1, newPartida.getNumeroNivel());
        assertEquals(controlador, newPartida.getControlador());
    }
    
    @Test
    public void testConstructorWithoutControlador() {
        Partida newPartida = new Partida(nivel, 0, 1);
        assertEquals(nivel, newPartida.getNivel());
        assertEquals(0, newPartida.getPuntuacionAbsoluta());
        assertEquals(1, newPartida.getNumeroNivel());
        assertNull(newPartida.getControlador());
    }
    
    @Test
    public void testSiguienteNivel() {
        //Assert False partida.siguienteNivel
        
        assertFalse(partida.siguienteNivel());
        assertEquals(2, partida.getNumeroNivel());
    }
    
    @Test
    public void testMover() throws InterruptedException {
        partida.mover('W');
        //TODO:
    }    
    
}
