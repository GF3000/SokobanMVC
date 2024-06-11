package es.upm.pproject.sokoban;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.controller.Controlador;
import es.upm.pproject.sokoban.model.Nivel;
import es.upm.pproject.sokoban.model.Partida;
import es.upm.pproject.sokoban.model.PartidaInterface;
import es.upm.pproject.sokoban.view.Vista;

public class ControladorTest {

    private Controlador controlador;
    private PartidaInterface partida;
    private Vista vista;
    private Nivel nivel;

    @BeforeEach
    public void setUp() throws Exception{
        nivel = new Nivel(1);
        partida = new Partida(nivel, 0, 1);
        vista = new Vista();
        controlador = new Controlador(vista, partida);
        partida.setControlador(controlador);
    }

    @Test
    @DisplayName("Test getVista")
    public void testGetVista() {
        assertEquals(vista, controlador.getVista());
    }

    @Test
    @DisplayName("Test getPartida")
    public void testGetPartida() {
        assertEquals(partida, controlador.getPartida());
    }

    @Test
    @DisplayName("Test ejecutarTecla")
    public void testEjecutarTecla() {
        // TODO: Add test logic
    }

    @Test
    @DisplayName("Test cargarPartida")
    public void testCargarPartida() {
        controlador.guardarPartida("test.xml");
        try{
            controlador.getPartida().mover('u');

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        controlador.cargarPartida("test.xml");
        assertEquals(0, controlador.getPartida().getPuntuacionAbsoluta());

        // Remove the file
        File file = new File("test.xml");
        file.delete();
    }


}