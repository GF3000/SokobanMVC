package es.upm.pproject.sokoban;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.upm.pproject.sokoban.exceptions.IncorrectLevelException;


@XmlRootElement(name = "tablero")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tablero {
    @XmlTransient
    private static final Logger LOGGER = LoggerFactory.getLogger(Tablero.class);

    @XmlElement
    private char[][] matriz;

    @XmlElement
    private String nombreNivel;

    // Constructor sin parámetros requerido por JAXB
    public Tablero() {
    }

    /**
     * Constructor de la clase Tablero
     * @param source 
     * @throws FileNotFoundException si el fichero no ha sido encontrado
     * @throws IncorrectLevelException si el nivel no está bien formado
     */
    public Tablero(String source) throws FileNotFoundException, IncorrectLevelException{
        try(Scanner scan = new Scanner(new File(source))){
            this.nombreNivel = scan.nextLine();
            int filas = scan.nextInt();
            int columnas = scan.nextInt();
            scan.nextLine();
            matriz = new char[filas][columnas];
            for(int i = 0; i < filas; i++){
                matriz[i] = scan.nextLine().toCharArray();
            }
            if(!nivelCorrecto()) throw new IncorrectLevelException();
            LOGGER.debug("Nuevo tablero {} creado", this);
        }
    }



    /**
     * Comprueba que el nivel está bien formado
     * @return true si el nivel está bien formado y false en caso contrario
     */
    private boolean nivelCorrecto(){
        int nCajas = 0;
        int nEndPoints = 0;
        for(int i = 0; i<matriz[0].length; i++){
            for(int j = 0; j<matriz.length; j++){
                if(matriz[i][j] == '*') nEndPoints++;
                else if(matriz[i][j] == '#') nCajas++;
                else if(matriz[i][j] == '_'){ nEndPoints++; nCajas++;}
                else if(matriz[i][j] == '&') nEndPoints++;
            }
        }
        return nCajas != 0 && nCajas == nEndPoints;
    }

    /**
     * Ejecuta un movimiento
     * @param direccion
     * @return true si se ha podido realizar el movimiento o false en caso contrario
     */
    public boolean mover(char direccion){
        boolean movimientoRealizado = false;
        int personajeFila = 0;
        int personajeColumna = 0; 
        for(int i = 0; i<matriz[0].length; i++){
            for(int j = 0; j<matriz.length; j++){
                if(matriz[i][j] == 'W' || matriz[i][j] == '&'){
                    personajeFila = i;
                    personajeColumna = j;
                }
            }
        }
        switch(direccion){
            case 'u': movimientoRealizado = moverArriba(personajeFila, personajeColumna); break;
            case 'd': movimientoRealizado = moverAbajo(personajeFila, personajeColumna); break;
            case 'l': movimientoRealizado = moverIzquierda(personajeFila, personajeColumna); break;
            case 'r': movimientoRealizado = moverDerecha(personajeFila, personajeColumna); break;
            default: break;
        }
        if(movimientoRealizado){
            LOGGER.debug("Movimiento {} realizado", direccion);
            return true;
        }
        LOGGER.debug("Movimiento {} no realizado", direccion);
        return false;
    }

    private boolean moverArriba(int personajeFila, int personajeColumna) {
        if (personajeFila <= 1 || 
            (personajeFila == 2 && 
            (matriz[0][personajeColumna] == '#' || 
            matriz[0][personajeColumna] == '_'))) {
            return false;
        }
        
        char prevChar = matriz[personajeFila - 1][personajeColumna];
        char prevPrevChar = matriz[personajeFila - 2][personajeColumna];
        char currentChar = matriz[personajeFila][personajeColumna];
        
        if (prevChar == '+' || prevPrevChar == '+' || prevChar == ' ' || prevChar == '*') {
            matriz[personajeFila - 1][personajeColumna] = currentChar == 'W' ? ' ' : 'W';
            matriz[personajeFila][personajeColumna] = currentChar == 'W' ? ' ' : '*';
        } else if (prevChar == '#' || prevChar == '_') {
            char replaceChar = prevChar == '#' ? (prevPrevChar == '*' ? '_' : '#') : '&';
            matriz[personajeFila - 1][personajeColumna] = 'W';
            matriz[personajeFila - 2][personajeColumna] = replaceChar;
            matriz[personajeFila][personajeColumna] = currentChar == 'W' ? ' ' : '*';
        }
        
        return true;
    }
    
    private boolean moverAbajo(int personajeFila, int personajeColumna) {
        if (personajeFila >= matriz.length - 2 || 
            (personajeFila == matriz.length - 3 && 
            (matriz[matriz.length - 1][personajeColumna] == '#' || 
            matriz[matriz.length - 1][personajeColumna] == '_'))) {
            return false;
        }
        
        char nextChar = matriz[personajeFila + 1][personajeColumna];
        char nextNextChar = matriz[personajeFila + 2][personajeColumna];
        char currentChar = matriz[personajeFila][personajeColumna];
        
        if (nextChar == '+' || nextNextChar == '+' || nextChar == ' ' || nextChar == '*') {
            matriz[personajeFila + 1][personajeColumna] = currentChar == 'W' ? ' ' : 'W';
            matriz[personajeFila][personajeColumna] = currentChar == 'W' ? ' ' : '*';
        } else if (nextChar == '#' || nextChar == '_') {
            char replaceChar = nextChar == '#' ? (nextNextChar == '*' ? '_' : '#') : '&';
            matriz[personajeFila + 1][personajeColumna] = 'W';
            matriz[personajeFila + 2][personajeColumna] = replaceChar;
            matriz[personajeFila][personajeColumna] = currentChar == 'W' ? ' ' : '*';
        }
        
        return true;
    }
    

    private boolean moverIzquierda(int personajeFila, int personajeColumna) {
        if (personajeColumna <= 1 || 
            (personajeColumna == 2 && 
            (matriz[personajeFila][0] == '#' || 
            matriz[personajeFila][0] == '_'))) {
            return false;
        }
        
        char prevChar = matriz[personajeFila][personajeColumna - 1];
        char prevPrevChar = matriz[personajeFila][personajeColumna - 2];
        char currentChar = matriz[personajeFila][personajeColumna];
        
        if (prevChar == '+' || prevPrevChar == '+' || prevChar == ' ' || prevChar == '*') {
            matriz[personajeFila][personajeColumna - 1] = currentChar == 'W' ? ' ' : 'W';
            matriz[personajeFila][personajeColumna] = currentChar == 'W' ? ' ' : '*';
        } else if (prevChar == '#' || prevChar == '_') {
            char replaceChar = prevChar == '#' ? (prevPrevChar == '*' ? '_' : '#') : '&';
            matriz[personajeFila][personajeColumna - 1] = 'W';
            matriz[personajeFila][personajeColumna - 2] = replaceChar;
            matriz[personajeFila][personajeColumna] = currentChar == 'W' ? ' ' : '*';
        }
        
        return true;
    }
    
    private boolean moverDerecha(int personajeFila, int personajeColumna) {
        if (personajeColumna >= matriz.length - 2) {
            return false;
        }
        
        char nextChar = matriz[personajeFila][personajeColumna + 1];
        char nextNextChar = matriz[personajeFila][personajeColumna + 2];
        char currentChar = matriz[personajeFila][personajeColumna];
        
        if (nextChar == '+' || nextNextChar == '+' || nextChar == ' ' || nextChar == '*') {
            matriz[personajeFila][personajeColumna + 1] = currentChar == 'W' ? ' ' : 'W';
            matriz[personajeFila][personajeColumna] = currentChar == 'W' ? ' ' : '*';
        } else if (nextChar == '#' || nextChar == '_') {
            char replaceChar = nextChar == '#' ? (nextNextChar == '*' ? '_' : '#') : '&';
            matriz[personajeFila][personajeColumna + 1] = 'W';
            matriz[personajeFila][personajeColumna + 2] = replaceChar;
            matriz[personajeFila][personajeColumna] = currentChar == 'W' ? ' ' : '*';
        }
        
        return true;
    }
    
    /**
     * Comprueba si se ha llegado al estado final
     * @return true si se ha alcanzado el estado final o false en caso contrario
     */
    public boolean comprobarFin(){
        boolean fin = true;
        int j, i = 0; 
        while(i<matriz.length && fin){
            j = 0;
            while(j<matriz[0].length && fin){
                if(matriz[i][j] == '*' || matriz[i][j] == '&'){
                    fin = false;
                }
                j++;
            }
            i++;
        }
        LOGGER.debug("Fin comprobado: {}", fin);
        return fin;
    }

    /**
     * Setter del atributo matriz
     * @param matriz 
     */
    public void setMatriz(char[][] matriz){
        this.matriz = matriz;
        LOGGER.debug("Matriz de Tablero {} modificada mediante el setter", this);
    }

    /**
     * Getter del atributo matriz
     * @return matriz
     */
    public char[][] getMatriz(){
        LOGGER.debug("Matriz de tablero {} obtenida", this);
        return matriz;
    }

    /**
     * Getter del atributo nombreNivel
     * @return nombreNivel
     */
    public String getNombreNivel(){
        LOGGER.debug("Nombre de nivel de tablero {} obtenido", this);
        return nombreNivel;
    }

    /**
     * Transforma la información del tablero a formato String
     * @return tablero en formato String
     */
    public String toString(){
        String s = nombreNivel+"\n";
        if (matriz == null) {
            return s;
        }
        if (matriz.length == 0) {
            return s;
        }
        for(int i = 0; i<matriz[0].length; i++){
            for(int j = 0; j<matriz.length; j++){
                s += matriz[i][j];
            }
            s += '\n';
        }
        LOGGER.debug("Tablero transformado a String");
        return s;
    }
}
