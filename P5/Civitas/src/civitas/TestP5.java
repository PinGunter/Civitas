package civitas;

import GUI.*;
import java.util.ArrayList;

/**
 *
 * @author salva
 */
public class TestP5 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CivitasView vista = new CivitasView();
        GUI.Dado.createInstance(vista);
        GUI.Dado.getInstance().setDebug(true);
        CapturaNombres captura_nombre = new CapturaNombres(vista,true);
        ArrayList<String> nombres = new ArrayList<>();
        nombres = captura_nombre.getNombres();
        CivitasJuego juego = new CivitasJuego(nombres);
        Controlador controller = new Controlador(juego,vista);
        vista.setCivitasJuego(juego);
        vista.actualizarVista();
        
    }
}
