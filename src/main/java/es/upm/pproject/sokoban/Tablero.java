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
public class Tablero implements TableroInterface{
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

    @Override
    public boolean nivelCorrecto(){
        int nCajas = 0;
        int nEndPoints = 0;
        for(int i = 0; i<matriz.length; i++){
            for(int j = 0; j<matriz[0].length; j++){
                if(matriz[i][j] == '*') nEndPoints++;
                else if(matriz[i][j] == '#') nCajas++;
                else if(matriz[i][j] == '_'){ nEndPoints++; nCajas++;}
                else if(matriz[i][j] == '&') nEndPoints++;
            }
        }
        return nCajas != 0 && nCajas == nEndPoints;
    }

    @Override
    public boolean mover(char direccion){
        boolean movimientoRealizado = false;
        int personajeFila = 0;
        int personajeColumna = 0; 
        for(int i = 0; i<matriz.length; i++){
            for(int j = 0; j<matriz[0].length; j++){
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
    
    @Override
    public boolean comprobarFin(){
        boolean fin = true;
        int j;
        int i = 0; 
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

    @Override
    public void setMatriz(char[][] matriz){
        this.matriz = matriz;
        LOGGER.debug("Matriz de Tablero {} modificada mediante el setter", this);
    }

    @Override
    public char[][] getMatriz(){
        LOGGER.debug("Matriz de tablero {} obtenida", this);
        return matriz;
    }

    @Override
    public String getNombreNivel(){
        LOGGER.debug("Nombre de nivel de tablero {} obtenido", this);
        return nombreNivel;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(nombreNivel+"\n");
        if (matriz == null || matriz.length == 0) {
            return sb.toString();
        }
        for(int i = 0; i<matriz.length; i++){
            for(int j = 0; j<matriz[0].length; j++){
                sb.append(matriz[i][j]);
            }
            sb.append('\n');
        }
        LOGGER.debug("Tablero transformado a String");
        return sb.toString();
    }
}
