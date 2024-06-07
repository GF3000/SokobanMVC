package es.upm.pproject.sokoban;

/*
 * Controlador	
	+ Partida
	+ Vista

	Contrutor: Controlador(vista, partida)

	- ejecutarTecla(char)//decide qué hacer cuando se pulsa una tecla, llama a partida.mover
	- cargarPartida(path) // Partida = new Partida
	- guardarPartida(path)
	- mostrar() // llama a Vista.pintar(Partida)
 */
public class Controlador {

    private static PartidaInterface partida;
    private Vista vista;

    /**
     * Constructor de la clase Controlador
     * @param vista
     * @param partida
     */
    public Controlador(Vista vista, PartidaInterface partida) {
        this.vista = vista;
        this.partida = partida;
    }

    //getVista
    public Vista getVista() {
        return vista;
    }
    //getPartida
    public PartidaInterface getPartida() {
        return partida;
    }

    /**
     * Método que decide qué hacer cuando se pulsa una tecla
     * @param tecla
     */
    public static void ejecutarTecla(char tecla) {
        try {
            partida.mover(tecla);
        } catch (Exception e) {
            e.printStackTrace();
        }

       
    }

    /**
     * Método que carga una partida
     * @param path
     */
    public void cargarPartida(String path) {
        //TODO: Cargar partida JSON
    }

    /**
     * Método que guarda una partida
     * @param path
     */
    public void guardarPartida(String path) {
        //TODO: Convertir partida a JSON
    }

    /**
     * Método que muestra la partida
     */
    public void actualizarTablero() {
        vista.pintar(partida);
    }

    /**
     * 
     */
    public void finPartida(){
        //TODO: 
    }

    
}
