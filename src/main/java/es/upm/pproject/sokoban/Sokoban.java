package es.upm.pproject.sokoban;

import javax.swing.SwingUtilities;

public class Sokoban {
    public static void main(String[] args) {
        try {
            Nivel n = new Nivel(1);
            Partida p = new Partida(n,0,1);
            Vista v = new Vista();
            Controlador c = new Controlador(v, p);
            c.getPartida().setControlador(c);
            c.getVista().setControlador(c);
            
            SwingUtilities.invokeLater(() -> {
                c.getVista().pintar(p);
                c.getVista().setVisible(true);
            });        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

