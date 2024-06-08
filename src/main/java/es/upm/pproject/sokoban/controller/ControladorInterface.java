package es.upm.pproject.sokoban.controller;

import es.upm.pproject.sokoban.PartidaInterface;
import es.upm.pproject.sokoban.view.Vista;

/**
 * Interface para el Controlador
 */
public interface ControladorInterface {

    /**
     * Obtiene la vista asociada con el controlador.
     * 
     * @return la vista
     */
    Vista getVista();

    /**
     * Obtiene la partida actual asociada con el controlador.
     * 
     * @return la partida actual
     */
    PartidaInterface getPartida();

    /**
     * Método que decide qué hacer cuando se pulsa una tecla.
     * 
     * @param tecla la tecla pulsada
     */
    void ejecutarTecla(char tecla);

    /**
     * Método que carga una partida desde una ruta especificada.
     * 
     * @param path la ruta del archivo de la partida
     */
    void cargarPartida(String path);

    /**
     * Método que guarda la partida actual en una ruta especificada.
     * 
     * @param path la ruta del archivo donde se guardará la partida
     */
    void guardarPartida(String path);

    /**
     * Método que actualiza el tablero llamando al método pintar de la vista.
     */
    void actualizarTablero();

    /**
     * Método que finaliza la partida actual.
     */
    void finPartida();
}
