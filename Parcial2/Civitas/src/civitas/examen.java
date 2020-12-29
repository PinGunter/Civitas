// INICIO EXAMEN
package civitas;

import java.util.ArrayList;
import juegoTexto.Controlador;
import juegoTexto.VistaTextual;

/**
 *
 * @author salva
 */
public class examen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // EN las capturas no se ve que empiece el gato primero porque se me paso hacerlo cuando hice las capturas,
        // ahora funciona bien ,empieza el gato siempre;
        ArrayList<String> jugadores = new ArrayList<>();
        jugadores.add("Perro");
        jugadores.add("Gato");
        jugadores.add("Caballo");
        VistaTextual vista = new VistaTextual();
        CivitasJuego juego = new CivitasJuego(jugadores);
        Dado.getInstance().setDebug(Boolean.TRUE);
        Controlador controlador = new Controlador(juego, vista);
        controlador.juega();    }
    
}

// FIN DE EXAMEN