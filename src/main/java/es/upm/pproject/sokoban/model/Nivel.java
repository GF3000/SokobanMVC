package es.upm.pproject.sokoban.model;

import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.LinkedList;
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
public class Nivel implements NivelInterface{
    @XmlTransient
    private static final Logger LOGGER = LoggerFactory.getLogger(Nivel.class);

    @XmlElement
    private Tablero tablero;

    @XmlElement
    private int puntuacionRelativa;

    @XmlElementWrapper(name = "estadosAnteriores")
    @XmlElement(name = "estado")
    private Deque<char[][]> estadosAnteriores;

    // Constructor sin parámetros requerido por JAXB
    public Nivel() {
        this.estadosAnteriores = new LinkedList<>();
    }

    /**
     * Constructor de la clase Nivel
     * @param numNivel 
     * @throws FileNotFoundException si el fichero no ha sido encontrado
     * @throws IncorrectLevelException si el nivel no está bien formado
     */
    public Nivel(int numNivel) throws FileNotFoundException, IncorrectLevelException {
        tablero = new Tablero(String.format("./src/main/resources/niveles/level_%d.txt", numNivel));
        puntuacionRelativa = 0;
        estadosAnteriores = new LinkedList<>();
        estadosAnteriores.add(tablero.getMatriz());
        LOGGER.debug("Nuevo nivel {} creado", this);
    }

    @Override
    public int getPuntuacionRelativa() {
        LOGGER.debug("Puntuación relativa de Nivel {} obtenida", this);
        return puntuacionRelativa;
    }

    @Override
    public Tablero getTablero() {
        LOGGER.debug("Tablero de Nivel {} obtenido", this);
        return tablero;
    }

    @Override
    public Deque<char[][]> getEstadosAnteriores() {
        LOGGER.debug("Estados anteriores de Nivel {} obtenidos", this);
        return estadosAnteriores;
    }

    @Override
    public void deshacer() {
        tablero.setMatriz(estadosAnteriores.pop()); 
        LOGGER.debug("Matriz de Nivel {} devuelta a estado anterior", this);
    }

    @Override
    public void incremetarPuntuacionRelativa() {
        puntuacionRelativa++;
        LOGGER.debug("Puntuación relativa de Nivel {} incrementada", this);
    }
}
