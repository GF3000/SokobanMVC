package es.upm.pproject.sokoban.controller;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.sokoban.model.Partida;
import es.upm.pproject.sokoban.model.PartidaInterface;
import es.upm.pproject.sokoban.view.Vista;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
            JAXBContext jaxbContext = JAXBContext.newInstance(Controlador.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Controlador controladorCargado = (Controlador) jaxbUnmarshaller.unmarshal(file);

            // Actualizar los campos del controlador actual con los del controlador cargado
            this.partida = controladorCargado.getPartida();
            this.vista = controladorCargado.getVista();

            // Opcional: actualizar la vista
            actualizarTablero();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void guardarPartida(String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(Controlador.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(this, new File(path));
            LOGGER.debug("Partida guardada en {}", path);
        } catch (JAXBException e) {
            LOGGER.error("Error al guardar la partida en {}: {}", path, e.getMessage());
        }
    }

    public void actualizarTablero() {
        vista.pintar(partida);
    }

    public void finPartida() {
        //TODO: 
    }
}
