package es.upm.pproject.sokoban;

import java.io.FileNotFoundException;
import java.util.Stack;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.upm.pproject.sokoban.exceptions.IncorrectLevelException;

@XmlRootElement(name = "nivel")
@XmlAccessorType(XmlAccessType.FIELD)
public class Nivel {
    @XmlTransient
    private static final Logger LOGGER = LoggerFactory.getLogger(Nivel.class);

    @XmlElement
    private Tablero tablero;

    @XmlElement
    private int puntuacionRelativa;

    @XmlElementWrapper(name = "estadosAnteriores")
    @XmlElement(name = "estado")
    private Stack<char[][]> estadosAnteriores;

    // Constructor sin parámetros requerido por JAXB
    public Nivel() {
        this.estadosAnteriores = new Stack<>();
    }

    /**
     * Constructor de la clase Nivel
     * @param numNivel 
     * @throws FileNotFoundException si el fichero no ha sido encontrado
     * @throws IncorrectLevelException si el nivel no está bien formado
     */
    public Nivel(int numNivel) throws FileNotFoundException, IncorrectLevelException {
        tablero = new Tablero(String.format("./niveles/level_%d.txt", numNivel));
        puntuacionRelativa = 0;
        estadosAnteriores = new Stack<>();
        estadosAnteriores.add(tablero.getMatriz());
        LOGGER.debug("Nuevo nivel {} creado", this);
    }

    /**
     * Getter del atributo puntuacionRelativa
     * @return puntuacionRelativa
     */
    public int getPuntuacionRelativa() {
        LOGGER.debug("Puntuación relativa de Nivel {} obtenida", this);
        return puntuacionRelativa;
    }

    /**
     * Getter del atributo tablero
     * @return tablero
     */
    public Tablero getTablero() {
        LOGGER.debug("Tablero de Nivel {} obtenido", this);
        return tablero;
    }

    /**
     * Getter del atributo estadosAnteriores
     * @return estadosAnteriores
     */
    public Stack<char[][]> getEstadosAnteriores() {
        LOGGER.debug("Estados anteriores de Nivel {} obtenidos", this);
        return estadosAnteriores;
    }

    /**
     * Deshace el último movimiento y vuelve a un estado anterior
     */
    public void deshacer() {
        tablero.setMatriz(estadosAnteriores.pop()); 
        LOGGER.debug("Matriz de Nivel {} devuelta a estado anterior", this);
    }

    /**
     * Incrementa la puntuacion relativa
     */
    public void incremetarPuntuacionRelativa() {
        puntuacionRelativa++;
        LOGGER.debug("Puntuación relativa de Nivel {} incrementada", this);
    }
}
