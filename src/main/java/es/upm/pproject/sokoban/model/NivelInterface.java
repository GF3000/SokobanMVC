package es.upm.pproject.sokoban.model;

import java.util.Deque;

public interface NivelInterface {
    
    /**
     * Getter del atributo puntuacionRelativa
     * @return puntuacionRelativa
     */
    public int getPuntuacionRelativa();

    /**
     * Getter del atributo tablero
     * @return tablero
     */
    public TableroInterface getTablero();

    /**
     * Getter del atributo estadosAnteriores
     * @return estadosAnteriores
     */
    public Deque<char[][]> getEstadosAnteriores();

    /**
     * Añade una matriz a la pila de estados anteriores 
     */
    public void pushInEstadosAnteriores(char[][] matriz);

    /**
     * Deshace el último movimiento y vuelve a un estado anterior
     * @return true si había un estado anterior al que volver
     */
    public boolean deshacer();

    /**
     * Incrementa la puntuacion relativa
     */
    public void incremetarPuntuacionRelativa();

    /**
     * Decrementa la puntuacion relativa
     */
    public void decrementarPuntuacionRelativa();
}
