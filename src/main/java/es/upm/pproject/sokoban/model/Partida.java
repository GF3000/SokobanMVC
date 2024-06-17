package es.upm.pproject.sokoban.model;

import java.io.FileNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import es.upm.pproject.sokoban.controller.Controlador;
import es.upm.pproject.sokoban.controller.ControladorInterface;
import es.upm.pproject.sokoban.exceptions.IncorrectLevelException;

@XmlRootElement(name = "partida")
@XmlAccessorType(XmlAccessType.FIELD)
public class Partida implements PartidaInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(Partida.class);

    @XmlElement(name = "nivel") // Nombre personalizado para el elemento XML
    private Nivel nivel;

    @XmlElement(name = "puntuacionAbsoluta") // Nombre personalizado para el elemento XML
    private int puntuacionAbsoluta;

    @XmlElement(name = "numeroNivel") // Nombre personalizado para el elemento XML
    private int numeroNivel;

    @XmlTransient
    private ControladorInterface controlador;

    /**
     * Constructor de la clase Partida.
     * 
     * @param nivel el nivel inicial de la partida
     * @param puntuacionAbsoluta la puntuacion absoluta inicial de la partida
     * @param numeroNivel el número del nivel inicial
     * @param controlador el controlador de la partida
     */
    public Partida(Nivel nivel, int puntuacionAbsoluta, int numeroNivel, Controlador controlador) {
        this.nivel = nivel;
        this.puntuacionAbsoluta = puntuacionAbsoluta;
        this.numeroNivel = numeroNivel;
        this.controlador = controlador;
    }

    /**
     * Constructor de la clase Partida sin el controlador.
     * 
     * @param nivel el nivel inicial de la partida
     * @param puntuacionAbsoluta la puntuacion absoluta inicial de la partida
     * @param numeroNivel el número del nivel inicial
     */
    public Partida(Nivel nivel, int puntuacionAbsoluta, int numeroNivel) {
        this.nivel = nivel;
        this.puntuacionAbsoluta = puntuacionAbsoluta;
        this.numeroNivel = numeroNivel;
    }

    // Constructor sin parámetros requerido por JAXB
    public Partida() {
    }

    @Override
    @XmlTransient
    public ControladorInterface getControlador() {
        return controlador;
    }

    @Override
    public void setControlador(ControladorInterface controlador) {
        this.controlador = controlador;
    }

    @Override
    public Nivel getNivel() {
        return nivel;
    }

    @Override
    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    @Override
    public int getPuntuacionAbsoluta() {
        return puntuacionAbsoluta;
    }

    @Override
    public void setPuntuacionAbsoluta(int puntuacionAbsoluta) {
        this.puntuacionAbsoluta = puntuacionAbsoluta;
    }

    @Override
    public int getNumeroNivel() {
        return numeroNivel;
    }

    @Override
    public void setNumeroNivel(int numeroNivel) {
        this.numeroNivel = numeroNivel;
    }

    @Override
    public boolean siguienteNivel() {
        try {
            nivel = new Nivel(++numeroNivel);
            return true;
        } catch (FileNotFoundException | IncorrectLevelException e) {
            return false;
        }
    }

    @Override
    public void mover(char direccion) throws InterruptedException {
        if (nivel.getTablero().mover(direccion)) {
            nivel.pushInEstadosAnteriores(nivel.getTablero().getMatriz());
            nivel.incremetarPuntuacionRelativa();
            puntuacionAbsoluta++;
            controlador.actualizarTablero();
            if (nivel.getTablero().comprobarFin()) {
                if (siguienteNivel())
                    controlador.actualizarTablero();
                else
                    controlador.finPartida();
            }
        }
    }

    @Override
    public void reiniciar() {

        int currentLevel = numeroNivel;
        try {
            nivel = new Nivel(currentLevel);
            this.puntuacionAbsoluta = 0;
        } catch (FileNotFoundException | IncorrectLevelException e) {
            LOGGER.error("Error al reiniciar el nivel {}: {}", currentLevel, e.getMessage());
        }
    }
}
