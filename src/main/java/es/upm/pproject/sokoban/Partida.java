package main.java.es.upm.pproject.sokoban;

public class Partida {

    private Nivel nivel;
    private int puntuacionAbsoluta;
    private int numeroNivel;
    private Controlador controlador;

    /**
     * Obtiene el controlador de la partida.
     * 
     * @return el controlador de la partida
     */
    public Controlador getControlador() {
        return controlador;
    }

    /**
     * Establece el controlador de la partida.
     * 
     * @param controlador el controlador a establecer
     */
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    /**
     * Obtiene el nivel actual de la partida.
     * 
     * @return el nivel actual
     */
    public Nivel getNivel() {
        return nivel;
    }

    /**
     * Establece el nivel de la partida.
     * 
     * @param nivel el nivel a establecer
     */
    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    /**
     * Obtiene la puntuacion absoluta de la partida.
     * 
     * @return la puntuacion absoluta
     */
    public int getPuntuacionAbsoluta() {
        return puntuacionAbsoluta;
    }

    /**
     * Establece la puntuacion absoluta de la partida.
     * 
     * @param puntuacionAbsoluta la puntuacion absoluta a establecer
     */
    public void setPuntuacionAbsoluta(int puntuacionAbsoluta) {
        this.puntuacionAbsoluta = puntuacionAbsoluta;
    }

    /**
     * Obtiene el número del nivel actual.
     * 
     * @return el número del nivel actual
     */
    public int getNumeroNivel() {
        return numeroNivel;
    }

    /**
     * Establece el número del nivel actual.
     * 
     * @param numeroNivel el número del nivel a establecer
     */
    public void setNumeroNivel(int numeroNivel) {
        this.numeroNivel = numeroNivel;
    }

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

    /**
     * Avanza al siguiente nivel de la partida.
     * 
     * @return true si el nivel fue incrementado exitosamente
     */
    public boolean siguienteNivel() {
        nivel = new Nivel(++numeroNivel);
        return true;
    }

    /**
     * Mueve el personaje en la dirección especificada.
     * 
     * @param direccion la dirección en la que se debe mover el personaje ('W', 'A', 'S', 'D' para arriba, izquierda, abajo, derecha respectivamente)
     */
    public void mover(char direccion) {
        if (nivel.getTablero().mover(direccion)) {
            nivel.getEstadosAnteriores().add(nivel.getTablero().getMatriz());
            controlador.actualizarTablero();
            if (nivel.getTablero().comprobarFin()) {
                // Espera 1000 ms antes de avanzar al siguiente nivel
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Error en el sleep");
                }
                if (siguienteNivel())
                    controlador.actualizarTablero();
                else
                    controlador.finPartida();
            }
        }
    }
}
