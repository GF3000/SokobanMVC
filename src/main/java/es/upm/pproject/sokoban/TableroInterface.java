package es.upm.pproject.sokoban;

public interface TableroInterface {

    /**
     * Comprueba que el nivel está bien formado
     * @return true si el nivel está bien formado y false en caso contrario
     */
    public boolean nivelCorrecto();

    /**
     * Ejecuta un movimiento
     * @param direccion
     * @return true si se ha podido realizar el movimiento o false en caso contrario
     */
    public boolean mover(char direccion);

    /**
     * Comprueba si se ha llegado al estado final
     * @return true si se ha alcanzado el estado final o false en caso contrario
     */
    public boolean comprobarFin();

    /**
     * Setter del atributo matriz
     * @param matriz 
     */
    public void setMatriz(char[][] matriz);

    /**
     * Getter del atributo matriz
     * @return matriz
     */
    public char[][] getMatriz();

    /**
     * Getter del atributo nombreNivel
     * @return nombreNivel
     */
    public String getNombreNivel();

    /**
     * Transforma la información del tablero a formato String
     * @return tablero en formato String
     */
    public String toString();

}
