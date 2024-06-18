package es.upm.pproject.sokoban.controller;

import java.awt.event.KeyEvent;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.sokoban.model.Nivel;
import es.upm.pproject.sokoban.model.Partida;
import es.upm.pproject.sokoban.model.PartidaInterface;
import es.upm.pproject.sokoban.model.Tablero;
import es.upm.pproject.sokoban.view.Vista;

@XmlRootElement(name = "controlador")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Partida.class, Nivel.class, Tablero.class, Vista.class})

public class Controlador implements ControladorInterface {

    @XmlElement
    private PartidaInterface partida;

    @XmlElement
    private Vista vista;

    private static final Logger LOGGER = LoggerFactory.getLogger(Controlador.class);

    /**
     * Constructor de la clase Controlador
     * @param vista
     * @param partida
     */
    public Controlador() {
        // Constructor vacío necesario para JAXB
    }

    public Controlador(Vista vista, PartidaInterface partida) {
        this.vista = vista;
        this.partida = partida;
    }

    public Vista getVista() {
        return vista;
    }

    public PartidaInterface getPartida() {
        return partida;
    }

    public void ejecutarTecla(int tecla) {
        try {
            switch (tecla) {
                case KeyEvent.VK_UP: partida.mover('u'); break;
                case KeyEvent.VK_LEFT: partida.mover('l'); break;
                case KeyEvent.VK_RIGHT: partida.mover('r'); break;
                case KeyEvent.VK_DOWN: partida.mover('d'); break;
                case KeyEvent.VK_Z: deshacer(); break;
                case KeyEvent.VK_R: reiniciarNivel(); break;
                default: break;
            }
        } catch (Exception e) {
            LOGGER.error("Error al ejecutar tecla {}: {}", tecla, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static final JAXBContext creteContext() throws JAXBException{
        return JAXBContext.newInstance(Partida.class);  
    }

    @Override
    public void cargarPartida(String path) {
        try {
            File file = new File(path);
            JAXBContext jaxbContext = creteContext();

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            this.partida = (PartidaInterface)jaxbUnmarshaller.unmarshal(file);
            this.partida.setControlador(this);
            

            // Opcional: actualizar la vista
            actualizarTablero();

        } catch (JAXBException e) {
            LOGGER.error("Error al cargar partida: {}", e.getMessage());
        }
    }

    public void guardarPartida(String path) {
        try {
            File file = new File(path);
            JAXBContext jaxbContext = creteContext();

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(partida, file);
        } catch (JAXBException e) {
            LOGGER.error("Error al guardar partida: {}", e.getMessage());
        }
    }

    public void actualizarTablero() {
        vista.pintar(partida);
    }

    public void deshacer() {
        partida.deshacer();
        actualizarTablero();
    }

    public void finPartida() {
        this.vista.mostrarFinPartida();
    }

    public void reiniciarNivel() {
        this.partida.reiniciar();
        actualizarTablero();
    }
        
}
