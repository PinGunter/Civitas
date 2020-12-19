// EXAMEN
package examen;

import civitas.Jugador;
import civitas.CivitasJuego;
import civitas.Dado;
import java.util.ArrayList;
import juegoTexto.Controlador;
import juegoTexto.VistaTextual;

/**
 *
 *
 * @author salva
 */
public class PruebaP3 {

    public static void main(String[] args) {

        ArrayList<String> jugadores = new ArrayList<>();
        jugadores.add("Salvador");
        jugadores.add("Romero");

        VistaTextual vista = new VistaTextual();
        CivitasJuego juego = new CivitasJuego(jugadores);
        Dado.getInstance().setDebug(Boolean.TRUE);
        Controlador controlador = new Controlador(juego, vista);
        controlador.juega();
    }
}

// FIN DE EXAMEN
