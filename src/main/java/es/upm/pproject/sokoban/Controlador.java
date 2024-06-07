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
public class Controlador implements ControladorInterface {

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


    public void cargarPartida(String path) {
        //TODO: Cargar partida JSON
    }

    public void guardarPartida(String path) {
        //TODO: Convertir partida a JSON
    }

    public void actualizarTablero() {
        vista.pintar(partida);
    }

    public void finPartida(){
        //TODO: 
    }

    
}
