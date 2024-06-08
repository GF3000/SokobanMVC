package es.upm.pproject.sokoban;

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
     * Deshace el último movimiento y vuelve a un estado anterior
     */
    public void deshacer();

    /**
     * Incrementa la puntuacion relativa
     */
    public void incremetarPuntuacionRelativa();
}
