package es.upm.pproject.sokoban.controller;

import java.io.File;

import javax.swing.text.TabExpander;
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

    public void ejecutarTecla(char tecla) {
        try {
            partida.mover(tecla);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cargarPartida(String path) {
        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(Partida.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Partida partida = (Partida) jaxbUnmarshaller.unmarshal(file);

            this.partida = partida;
            this.partida.setControlador(this);
            

            // Opcional: actualizar la vista
            actualizarTablero();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void guardarPartida(String path) {
        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(Partida.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(partida, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void actualizarTablero() {
        vista.pintar(partida);
    }

    public void finPartida() {
        //TODO: 
    }
}
