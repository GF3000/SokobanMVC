package es.upm.pproject.sokoban;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.upm.pproject.sokoban.exceptions.IncorrectLevelException;

public class Tablero {
    private static final Logger LOGGER = LoggerFactory.getLogger(Tablero.class);
    private char matriz[][];
    private String nombreNivel;

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
        int nCajas = 0, nEndPoints = 0;
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
        int personajeFila = 0, personajeColumna = 0; 
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
        }
        if(movimientoRealizado){
            LOGGER.debug("Movimiento {} realizado", direccion);
            return true;
        }
        LOGGER.debug("Movimiento {} no realizado", direccion);
        return false;
    }

    private boolean moverArriba(int personajeFila, int personajeColumna){
        if(personajeFila == 0 || 
           (personajeFila == 1 && 
           (matriz[0][personajeColumna] == '#' || 
           matriz[0][personajeColumna] == '_')) ||
           (personajeFila > 1 && 
           (matriz[personajeFila-1][personajeColumna] == '+' ||
           (matriz[personajeFila-1][personajeColumna] == '#' || 
           matriz[personajeFila-1][personajeColumna] == '_') && 
           (matriz[personajeFila-2][personajeColumna] == '+' ||
           matriz[personajeFila-2][personajeColumna] == '#' || 
           matriz[personajeFila-2][personajeColumna] == '_')))){
            return false;
        }else{
            if(matriz[personajeFila-1][personajeColumna] == ' '){
                matriz[personajeFila-1][personajeColumna] = 'W';
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila-1][personajeColumna] == '#'){
                matriz[personajeFila-1][personajeColumna] = 'W';
                if(matriz[personajeFila-2][personajeColumna] == '*'){
                    matriz[personajeFila-2][personajeColumna] = '_';
                }else{
                    matriz[personajeFila-2][personajeColumna] = '#';
                }
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila-1][personajeColumna] == '_'){
                matriz[personajeFila-1][personajeColumna] = '&';
                if(matriz[personajeFila-2][personajeColumna] == '*'){
                    matriz[personajeFila-2][personajeColumna] = '_';
                }else{
                    matriz[personajeFila-2][personajeColumna] = '#';
                }
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila-1][personajeColumna] == '*'){
                matriz[personajeFila-1][personajeColumna] = '&';
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }
        }
        return true;
    }

    private boolean moverAbajo(int personajeFila, int personajeColumna){
        if(personajeFila == matriz[0].length-1 || 
           personajeFila == matriz[0].length-2 && 
           (matriz[matriz[0].length-1][personajeColumna] == '#' || 
           matriz[matriz[0].length-1][personajeColumna] == '_') ||
           (personajeFila < matriz[0].length-2 &&
           (matriz[personajeFila+1][personajeColumna] == '+' ||
           (matriz[personajeFila+1][personajeColumna] == '#' || 
           matriz[personajeFila+1][personajeColumna] == '_') && 
           (matriz[personajeFila+2][personajeColumna] == '+' ||
           matriz[personajeFila+2][personajeColumna] == '#' || 
           matriz[personajeFila+2][personajeColumna] == '_')))){
            return false;
        }else{
            if(matriz[personajeFila+1][personajeColumna] == ' '){
                matriz[personajeFila+1][personajeColumna] = 'W';
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila+1][personajeColumna] == '#'){
                matriz[personajeFila+1][personajeColumna] = 'W';
                if(matriz[personajeFila+2][personajeColumna] == '*'){
                    matriz[personajeFila+2][personajeColumna] = '_';
                }else{
                    matriz[personajeFila+2][personajeColumna] = '#';
                }
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila+1][personajeColumna] == '_'){
                matriz[personajeFila+1][personajeColumna] = '&';
                if(matriz[personajeFila+2][personajeColumna] == '*'){
                    matriz[personajeFila+2][personajeColumna] = '_';
                }else{
                    matriz[personajeFila+2][personajeColumna] = '#';
                }
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila+1][personajeColumna] == '*'){
                matriz[personajeFila+1][personajeColumna] = '&';
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }
        }
        return true;
    }

    private boolean moverIzquierda(int personajeFila, int personajeColumna){
        if(personajeColumna == 0 || 
           (personajeColumna == 1 && 
           (matriz[personajeFila][0] == '#' || 
           matriz[personajeFila][0] == '_')) ||
           (personajeColumna > 1 && 
           (matriz[personajeFila][personajeColumna-1] == '+' ||
           (matriz[personajeFila][personajeColumna-1] == '#' || 
           matriz[personajeFila][personajeColumna-1] == '_') && 
           (matriz[personajeFila][personajeColumna-2] == '+' ||
           matriz[personajeFila][personajeColumna-2] == '#' || 
           matriz[personajeFila][personajeColumna-2] == '_')))){
            return false;
        }else{
            if(matriz[personajeFila][personajeColumna-1] == ' '){
                matriz[personajeFila][personajeColumna-1] = 'W';
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila][personajeColumna-1] == '#'){
                matriz[personajeFila][personajeColumna-1] = 'W';
                if(matriz[personajeFila][personajeColumna-2] == '*'){
                    matriz[personajeFila][personajeColumna-2] = '_';
                }else{
                    matriz[personajeFila][personajeColumna-2] = '#';
                }
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila][personajeColumna-1] == '_'){
                matriz[personajeFila][personajeColumna-1] = '&';
                if(matriz[personajeFila][personajeColumna-2] == '*'){
                    matriz[personajeFila][personajeColumna-2] = '_';
                }else{
                    matriz[personajeFila][personajeColumna-2] = '#';
                }
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila][personajeColumna-1] == '*'){
                matriz[personajeFila][personajeColumna-1] = '&';
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }
        }
        return true;
    }

    private boolean moverDerecha(int personajeFila, int personajeColumna){
        if(personajeColumna == matriz.length-1 || 
            personajeColumna == matriz.length-2 && 
           (matriz[personajeFila][matriz.length-1] == '#' || 
           matriz[personajeFila][matriz.length-1] == '_') ||
           (personajeColumna < matriz.length-2 &&
           (matriz[personajeFila][personajeColumna+1] == '+' ||
           (matriz[personajeFila][personajeColumna+1] == '#' || 
           matriz[personajeFila][personajeColumna+1] == '_') && 
           (matriz[personajeFila][personajeColumna+2] == '+' ||
           matriz[personajeFila][personajeColumna+2] == '#' || 
           matriz[personajeFila][personajeColumna+2] == '_')))){
            return false;
        }else{
            if(matriz[personajeFila][personajeColumna+1] == ' '){
                matriz[personajeFila][personajeColumna+1] = 'W';
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila][personajeColumna+1] == '#'){
                matriz[personajeFila][personajeColumna+1] = 'W';
                if(matriz[personajeFila][personajeColumna+2] == '*'){
                    matriz[personajeFila][personajeColumna+2] = '_';
                }else{
                    matriz[personajeFila][personajeColumna+2] = '#';
                }
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila][personajeColumna+1] == '_'){
                matriz[personajeFila][personajeColumna+1] = '&';
                if(matriz[personajeFila][personajeColumna+2] == '*'){
                    matriz[personajeFila][personajeColumna+2] = '_';
                }else{
                    matriz[personajeFila][personajeColumna+2] = '#';
                }
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }else if(matriz[personajeFila][personajeColumna+1] == '*'){
                matriz[personajeFila][personajeColumna+1] = '&';
                if(matriz[personajeFila][personajeColumna] == 'W'){
                    matriz[personajeFila][personajeColumna] = ' ';
                }else{
                    matriz[personajeFila][personajeColumna] = '*';
                }
            }
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
                if(matriz[i][j++] == '*' || matriz[i][j++] == '&'){
                    fin = false;
                }
            }
            i++;
        }
        LOGGER.debug("Fin comprobado: {}", fin);
        return true;
    }

    /**
     * Setter del atributo matriz
     * @param matriz 
     */
    public void setMatriz(char matriz[][]){
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
        for(int i = 0; i<matriz[0].length; i++){
            for(int j = 0; j<matriz.length; j++){
                s += matriz[i][j];
            }
            s += '\n';
        }
        LOGGER.debug("Tablero transformado a String");
        return s;
    }
    public static void main(String[] args) {
        try{
            Tablero t = new Tablero("./niveles/level_1.txt");
            System.out.println(t.toString());
            t.mover('r');
            System.out.println(t.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
