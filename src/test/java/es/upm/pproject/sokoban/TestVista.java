package es.upm.pproject.sokoban;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.exceptions.IncorrectLevelException;

class TestVista {
    private Vista vista;
    private Partida partida;
    private Controlador controlador;

    @BeforeEach
    void setUp() {
        vista = new Vista(); 
    }

    @Test
    @DisplayName("Test Constructor: inicialización correcta de componentes")
    void testInicializacionComponentes() {
        assertNotNull(vista.getContentPane(), "El contenedor de Vista no debe ser nulo");
        assertTrue(vista.getLayout() instanceof FlowLayout, "El layout debe ser FlowLayout");
        assertTrue(vista.getDefaultCloseOperation() == JFrame.EXIT_ON_CLOSE, "La operación de cierre por defecto debe ser EXIT_ON_CLOSE");
        assertTrue(vista.isFocusable(), "Vista debe ser focusable para recibir eventos de teclado");
    }

    @Test
    @DisplayName("Test Constructor: datos correctos")
    void testConstructorVistaConConstructor(){
        vista = new Vista();
        assertNotNull(vista);
    }

    @Test
    @DisplayName("Test Constructor: Inicialización de JLabels y fondo")
    void testInicializacionJLabelsYFondo() {
        assertEquals(Color.gray, vista.getContentPane().getBackground(), "El color de fondo debe ser gris");
        assertNotNull(vista.getLevelLabel(), "El JLabel de nivel debe inicializarse");
        assertNotNull(vista.getPointsLabel(), "El JLabel de puntos debe inicializarse");
        assertEquals("Level: ", vista.getLevelLabel().getText(), "El texto inicial del JLabel de nivel debe ser 'Level: '");
        assertEquals("Points: ", vista.getPointsLabel().getText(), "El texto inicial del JLabel de puntos debe ser 'Points: '");
    }

    @Test
    @DisplayName("Test Constructor: Carga de imágenes")
    void testCargaDeImagenes() {
        assertFalse(vista.getImages().isEmpty(), "El HashMap de imágenes no debe estar vacío");
        assertNotNull(vista.getImages().get("#"), "Debe existir una imagen para la caja");
        assertNotNull(vista.getImages().get("W"), "Debe existir una imagen para el jugador");
        assertNotNull(vista.getImages().get("+"), "Debe existir una imagen para la pared");
        assertNotNull(vista.getImages().get(" "), "Debe existir una imagen para el suelo");
        assertNotNull(vista.getImages().get("*"), "Debe existir una imagen para el objetivo");
    }

    @Test
    @DisplayName("Test Constructor: Configuración de KeyListener")
    void testConfiguracionKeyListener() {
        KeyListener[] keyListeners = vista.getKeyListeners();
        assertTrue(keyListeners.length > 0, "Debe haber al menos un KeyListener registrado");
        assertTrue(keyListeners[0] instanceof KeyAdapter, "El KeyListener debe ser una instancia de KeyAdapter");
    }
  
    @Test
    @DisplayName("Test carga de imágenes y mapeo de caracteres")
    void testCargaImagenesYMapeo() {
        Vista vista = new Vista(); 
        HashMap<String, BufferedImage> images = vista.getImages();

        // Verificar que todas las imágenes esperadas están cargadas
        assertNotNull(images.get(" "), "La imagen para ' ' (espacio) debe estar cargada");
        assertNotNull(images.get("+"), "La imagen para '+' debe estar cargada");
        assertNotNull(images.get("*"), "La imagen para '*' debe estar cargada");
        assertNotNull(images.get("#"), "La imagen para '#' debe estar cargada");
        assertNotNull(images.get("W"), "La imagen para 'W' debe estar cargada");

        // Verificar que el tamaño del HashMap es el esperado
        assertEquals(5, images.size(), "Debe haber exactamente 5 imágenes cargadas y mapeadas");
    }

    //test para drawMatrizPix
    @Test
    @DisplayName("Test drawMatrizPix")
    void testDrawMatrizPix() {
        Vista vista = new Vista(); 
        char[][] matriz = new char[][] {
            {' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' '}
        };
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        vista.drawMatrizPix(matriz, g);
        // Verificar que se dibujaron todas las celdas
        assertEquals(25, g.getClipBounds().width * g.getClipBounds().height, "Se deben dibujar todas las celdas de la matriz");
    }

    //testeamos pintar 
    @Test
    @DisplayName("Test pintar")
    void testPintar() throws FileNotFoundException, IncorrectLevelException {
        Vista vista = new Vista();
        Partida partida = new Partida(new Nivel(1), 0, 1);
        vista.pintar(partida);
        // Verificar que se crearon los JLabels de nivel y puntos
        assertNotNull(vista.getLevelLabel(), "El JLabel de nivel debe crearse");
        assertNotNull(vista.getPointsLabel(), "El JLabel de puntos debe crearse");
        // Verificar que se creó un JPanel
        List<JLabel> labels = new ArrayList<>();
        for (int i = 0; i < vista.getContentPane().getComponentCount(); i++) {
            if (vista.getContentPane().getComponent(i) instanceof JLabel) {
                labels.add((JLabel) vista.getContentPane().getComponent(i));
            }
        }
        assertEquals(2, labels.size(), "Debe haber exactamente 2 JLabels en el JFrame");
    }
    
    
}