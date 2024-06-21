package es.upm.pproject.sokoban.model;

import es.upm.pproject.sokoban.controller.ControladorInterface;

public interface PartidaInterface {

     /**
     * Obtiene el controlador de la partida.
     * 
     * @return el controlador de la partida
     */
    public ControladorInterface getControlador();

    /**
     * Establece el controlador de la partida.
     * 
     * @param controlador el controlador a establecer
     */
    public void setControlador(ControladorInterface controlador);

    /**
     * Obtiene el nivel actual de la partida.
     * 
     * @return el nivel actual
     */
    public NivelInterface getNivel();

    /**
     * Establece el nivel de la partida.
     * 
     * @param nivel el nivel a establecer
     */
    public void setNivel(Nivel nivel);

    /**
     * Obtiene la puntuacion absoluta de la partida.
     * 
     * @return la puntuacion absoluta
     */
    public int getPuntuacionAbsoluta();

    /**
     * Obtiene el número del nivel actual.
     * 
     * @return el número del nivel actual
     */
    public void setPuntuacionAbsoluta(int puntuacionAbsoluta);

    
    /**
     * Obtiene el número del nivel actual.
     * 
     * @return el número del nivel actual
     */
    public int getNumeroNivel();

    /**
     * Establece el número del nivel actual.
     * 
     * @param numeroNivel el número del nivel a establecer
     */
    public void setNumeroNivel(int numeroNivel);

    /**
     * Avanza al siguiente nivel de la partida.
     * 
     * @return true si el nivel fue incrementado exitosamente
     */
    public boolean siguienteNivel();

    /**
     * Mueve el personaje en la dirección especificada.
     * 
     * @param direccion la dirección en la que se debe mover el personaje ('W', 'A', 'S', 'D' para arriba, izquierda, abajo, derecha respectivamente)
     * @throws InterruptedException 
     */
    void mover(char direccion) throws InterruptedException;


    /**
     * Reiniciar el nivel
     * @return true si se ha podido reiniciar y false en caso contrario
     */
    boolean reiniciar();

    /**
     * Deshacer el último movimiento realizado.
     */
    void deshacer();
}
