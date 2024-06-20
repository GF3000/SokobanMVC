package es.upm.pproject.sokoban.view;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import es.upm.pproject.sokoban.controller.ControladorInterface;
import es.upm.pproject.sokoban.model.PartidaInterface;
import es.upm.pproject.sokoban.model.TableroInterface;


public class Vista extends JFrame {
    private transient HashMap<String, BufferedImage> images; // set of images
    private int size = 64; // size of the tile
    private transient ControladorInterface c; // creo que esto finalmente no hace falta aqui
    private String fontTexto = "Arial";

    // labels para nivel, puntos y total
    private JLabel levelLabel;
    private JLabel pointsLabel;
    private JLabel totalPoints;


    /**
     * Constructor de la clase Vista
     */

    public Vista() {

        images = new HashMap<>();
        try {
            loadImages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // establecer fondo a color
        getContentPane().setBackground(Color.gray);
        setTitle("Sokoban");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER)); // para alinear los JLabel horizontalmente

        // labels para nivel y puntos
        levelLabel = new JLabel("Level: ");
        levelLabel.setForeground(Color.black);
        levelLabel.setFont(new Font(fontTexto, Font.BOLD, 20));
        add(levelLabel);

        pointsLabel = new JLabel("Points: ");
        pointsLabel.setForeground(Color.WHITE);
        pointsLabel.setFont(new Font(fontTexto, Font.BOLD, 20));
        add(pointsLabel);

        totalPoints = new JLabel("Total: ");
        totalPoints.setForeground(Color.WHITE);
        totalPoints.setFont(new Font(fontTexto, Font.BOLD, 20));
        add(totalPoints);

        //MENU DESPLEGABLE DE MANU
        //1. Creo la barra del menú 
        JMenuBar barraMenu = new JMenuBar();
        //2. Creo los menús
        JMenu menuJuego = new JMenu("Menú");
        //3. Creo los elementos del menú: cargar partida guardada, guardar partida, salir
        JMenuItem cargarPartida = new JMenuItem("Cargar partida");
        JMenuItem guardarPartida = new JMenuItem("Guardar partida");
        JMenuItem salir = new JMenuItem("Salir");
        JMenuItem reiniciar = new JMenuItem("Reiniciar (r)");
        JMenuItem deshacer = new JMenuItem("Deshacer (z)");

        //4. Añadir manejadores de eventos a los elems del menú 
        cargarPartida.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar partida guardada");
            int userSelection = fileChooser.showOpenDialog(this);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                c.cargarPartida(fileToLoad.getAbsolutePath());
            }
            
        });
        guardarPartida.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar partida");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
        
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                // Asegúrate de que el archivo tenga la extensión correcta
                if (!fileToSave.getAbsolutePath().endsWith(".xml")) {
                    fileToSave = new File(fileToSave + ".xml");
                }
                // Verificar si el archivo ya existe
                if (fileToSave.exists()) {
                    int response = JOptionPane.showConfirmDialog(this, "El archivo ya existe. ¿Quieres sobrescribirlo?", "Confirmar sobrescritura", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        c.guardarPartida(fileToSave.getAbsolutePath());
                    }
                } else {
                    c.guardarPartida(fileToSave.getAbsolutePath());
                }
            }
        });
        salir.addActionListener(e -> 
            System.exit(0) //cierra la app
        );

        reiniciar.addActionListener(e -> 
            c.reiniciarNivel()
        );

        deshacer.addActionListener(e -> 
            c.deshacer()
        );

        //5. Añadimos elementos al menú
        menuJuego.add(deshacer);
        menuJuego.add(reiniciar);
        menuJuego.addSeparator();
        menuJuego.add(cargarPartida);
        menuJuego.add(guardarPartida);
        menuJuego.addSeparator();
        menuJuego.add(salir);
       
        //6. Añadir menús a la barra de menú
        barraMenu.add(menuJuego);

        //7. Añadimos la barra de menú a la ventana 
        setJMenuBar(barraMenu);


        // tema de keypressed
        setFocusable(true);//NO SE SI ES NECESARIO: se usa para que la ventana pueda recibir eventos del teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyPressed(e.getKeyCode());
            }
            private void keyPressed(int key) {
                // if any key is pressed, the controler will be called
                c.ejecutarTecla(key);
            }
        });
        
    }

    public void setControlador(ControladorInterface c) {
        this.c = c;
    }
    // auxiliar method to get the images and associate them with the characters of the matrix that represent 
    // different elements of the game
    private void loadImages() throws IOException{
        // reminder of the symbols used
        // + : Wall
        // . : Empty square // tambien " "
        // * : Goal Position
        // # : Box
        // W : Warehouse man
        try {
            images.put("#", ImageIO.read(getClass().getResource("./images/cajaInt.png")));
            images.put("W", ImageIO.read(getClass().getResource("./images/man.png")));
            images.put("&", ImageIO.read(getClass().getResource("./images/man.png")));
            images.put("_", ImageIO.read(getClass().getResource("./images/cajaInt.png")));

            images.put("+", ImageIO.read(getClass().getResource("./images/wall.png")));
            images.put(" ", ImageIO.read(getClass().getResource("./images/suelo2.png")));
            images.put("*", ImageIO.read(getClass().getResource("./images/sueloPunto2.png")));
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }
   
    public void drawMatrizPix(char[][] matriz, Graphics g) {
        int numColumns = matriz[0].length;
        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < numColumns; x++) {
                // each elemnt has a different image associted so we have to get the image
                // and create a "matrix of images" to rrepresent the game
                String e = matriz[y][x] + "";
                BufferedImage img = images.get(e);

                // draw every image with the size of the tile
                g.drawImage(img, x * size, y * size, size, size, null);
            }
        }
    }

    public void pintar(PartidaInterface partida) {
        //borrar lo que habia antes de repintar
        getContentPane().removeAll();

        // necesitamos la matriz de tablero para dibujarla
        TableroInterface tab = partida.getNivel().getTablero();
        char[][] matriz = tab.getMatriz();
       
        // Actualizar el JLabel con el nivel actual
        levelLabel.setText(partida.getNivel().getTablero().getNombreNivel());

        int total = partida.getPuntuacionAbsoluta();
        int points = partida.getNivel().getPuntuacionRelativa();
        
        // Actualizar los JLabel de puntos y total
        pointsLabel.setText("Points: " + points);
        totalPoints.setText("Total: " + total);

        // Agregar los JLabel al JFrame
        getContentPane().add(levelLabel);
        getContentPane().add(totalPoints);
        getContentPane().add(pointsLabel);


        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMatrizPix(matriz,g);
            }
        };

        //  Ajustar el tamaño del panel al tamaño de la matriz
        panel.setPreferredSize(new Dimension(matriz[0].length * size, matriz.length * size));
        
        // Agregar el panel al JFrame
        getContentPane().add(panel);
        repaint();
        revalidate();
    }

    public JLabel getLevelLabel() {
        return levelLabel;
    }
    
    public JLabel getPointsLabel() {
        return pointsLabel;
    }

    public JLabel getTotalPoints() {
        return totalPoints;
    }
    
    public Map<String, BufferedImage> getImages() {
        return images;
    }

    public void mostrarFinPartida() {
        JOptionPane.showMessageDialog(this, "¡Enhorabuena! Has completado todos los niveles", "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
        dispose();   
    }
  
}