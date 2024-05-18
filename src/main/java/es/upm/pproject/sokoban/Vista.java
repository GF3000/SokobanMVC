package es.upm.pproject.sokoban;

import java.io.IOException;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Vista extends JFrame {
    private HashMap<String, BufferedImage> images; // set of images
    private int size = 64; // size of the tile
    // private Controlador controlador; // creo que esto finalmente no hace falta aqui

    // labels para nivel, puntos y total
    private JLabel levelLabel;
    private JLabel pointsLabel;

    public Vista() {
        images = new HashMap<String, BufferedImage>();
        loadImages();
        // establecer fondo a color
        getContentPane().setBackground(Color.gray);
        setTitle("Sokoban");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER)); // para alinear los JLabel horizontalmente

        // labels para nivel y puntos
        levelLabel = new JLabel("Level: ");
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(levelLabel);

        pointsLabel = new JLabel("Points: ");
        pointsLabel.setForeground(Color.WHITE);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(pointsLabel);

        // tema de keypressed
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();
                System.out.println("key pressed: " + key);
                keyPressed(key);
            }
            private void keyPressed(char key) {
                // if any key is pressed, the controler will be called
                Controlador.ejecutarTecla(key);
            }
        });
        
    }

    // auxiliar method to get the images and associate them with the characters of the matrix that represent 
    // different elements of the game
    private void loadImages() {
        // reminder of the symbols used
        // + : Wall
        // . : Empty square // tambien " "
        // * : Goal Position
        // # : Box
        // W : Warehouse man
        try {
            images.put("#", ImageIO.read(getClass().getResource("./images/cajaInt.png")));
            images.put("W", ImageIO.read(getClass().getResource("./images/man.png")));
            images.put("+", ImageIO.read(getClass().getResource("./images/wall.png")));
            images.put(" ", ImageIO.read(getClass().getResource("./images/suelo2.png")));
            images.put("*", ImageIO.read(getClass().getResource("./images/sueloPunto2.png")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    private void drawMatrizPix(char[][] matriz, Graphics g) {
        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[0].length; x++) {
                // each elemnt has a different image associted so we have to get the image
                // and create a "matrix of images" to rrepresent the game
                String e = matriz[y][x] + "";
                BufferedImage img = images.get(e);

                // draw every image with the size of the tile
                g.drawImage(img, x * size, y * size, size, size, null);
            }
        }
    }

    public void pintar(Partida partida) {

        // necesitamos la matriz de tablero para dibujarla
        Tablero tab = partida.getNivel().getTablero();
        char[][] matriz = tab.getMatriz();
       
        // Actualizar el JLabel con el nivel actual
        levelLabel.setText("Level: " + partida.getNumeroNivel());

        int points = (int)partida.getPuntuacionAbsoluta();
        
        // Actualizar los JLabel de puntos y total
        pointsLabel.setText("Points: " + points);

        // Agregar los JLabel al JFrame
        getContentPane().add(levelLabel);
        getContentPane().add(pointsLabel);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMatrizPix(matriz,g);
            }
        };
        getContentPane().add(panel);

        //  Ajustar el tamaño del panel al tamaño de la matriz
        panel.setPreferredSize(new Dimension(matriz[0].length * size, matriz.length * size));
        
        // Agregar el panel al JFrame
        getContentPane().add(panel);
    }
   
    public static void main(String[] args) {
        try {
            Nivel n = new Nivel(1);
            Partida p = new Partida(n,0,1);
            // Controlador c = new Controlador(new Vista(), p);
            
            SwingUtilities.invokeLater(() -> {
                Vista vista = new Vista();
                vista.pintar(p);
                vista.setVisible(true);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}