package es.upm.pproject.sokoban;

import java.io.FileNotFoundException;

import javax.swing.SwingUtilities;
import es.upm.pproject.sokoban.controller.Controlador;
import es.upm.pproject.sokoban.controller.ControladorInterface;
import es.upm.pproject.sokoban.exceptions.IncorrectLevelException;
import es.upm.pproject.sokoban.model.Nivel;
import es.upm.pproject.sokoban.model.Partida;
import es.upm.pproject.sokoban.model.PartidaInterface;
import es.upm.pproject.sokoban.view.Vista;

public class Sokoban {
    public static void main(String[] args) throws FileNotFoundException, IncorrectLevelException{
        Nivel n = new Nivel(1);
        PartidaInterface p = new Partida(n,0,1);
        Vista v = new Vista();
        ControladorInterface c = new Controlador(v, p);
        c.getPartida().setControlador(c);
        c.getVista().setControlador(c);
        
        SwingUtilities.invokeLater(() -> {
            c.getVista().pintar(p);
            c.getVista().setVisible(true);
        });        
    }
}

