package es.upm.pproject.sokoban;

import java.io.FileNotFoundException;
import es.upm.pproject.sokoban.exceptions.IncorrectLevelException;

public class Partida implements PartidaInterface{

    private Nivel nivel;
    private int puntuacionAbsoluta;
    private int numeroNivel;
    private Controlador controlador;

     /**
     * Constructor de la clase Partida.
     * 
     * @param nivel el nivel inicial de la partida
     * @param puntuacionAbsoluta la puntuacion absoluta inicial de la partida
     * @param numeroNivel el número del nivel inicial
     * @param controlador el controlador de la partida
     */
    public Partida(Nivel nivel, int puntuacionAbsoluta, int numeroNivel, Controlador controlador) {
        this.nivel = nivel;
        this.puntuacionAbsoluta = puntuacionAbsoluta;
        this.numeroNivel = numeroNivel;
        this.controlador = controlador;
    }

    /**
     * Constructor de la clase Partida sin el controlador.
     * 
     * @param nivel el nivel inicial de la partida
     * @param puntuacionAbsoluta la puntuacion absoluta inicial de la partida
     * @param numeroNivel el número del nivel inicial
     */
    public Partida(Nivel nivel, int puntuacionAbsoluta, int numeroNivel) {
        this.nivel = nivel;
        this.puntuacionAbsoluta = puntuacionAbsoluta;
        this.numeroNivel = numeroNivel;
    }

    @Override
    public Controlador getControlador() {
        return controlador;
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public Nivel getNivel() {
        return nivel;
    }

    @Override
    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    @Override
    public int getPuntuacionAbsoluta() {
        return puntuacionAbsoluta;
    }

    @Override
    public void setPuntuacionAbsoluta(int puntuacionAbsoluta) {
        this.puntuacionAbsoluta = puntuacionAbsoluta;
    }

    @Override
    public int getNumeroNivel() {
        return numeroNivel;
    }

    @Override
    public void setNumeroNivel(int numeroNivel) {
        this.numeroNivel = numeroNivel;
    }

   

    @Override
    public boolean siguienteNivel() {
        try{
            nivel = new Nivel(++numeroNivel);
            return true;
        }catch(FileNotFoundException | IncorrectLevelException e){
            return false;
        }

    }

    @Override
    public void mover(char direccion) throws InterruptedException {
        if (nivel.getTablero().mover(direccion)) {
            nivel.getEstadosAnteriores().add(nivel.getTablero().getMatriz());
            controlador.actualizarTablero();
            if (nivel.getTablero().comprobarFin()) {
                System.out.println("mal");
                // Espera 1000 ms antes de avanzar al siguiente nivel
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Error en el sleep");
                    throw e; // Rethrow the InterruptedException
                }
                if (siguienteNivel())
                    controlador.actualizarTablero();
                else
                    controlador.finPartida();
            }
        }
    }

    @Override
    public void reiniciar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reiniciar'");
    }
}
