package civitas;

import java.util.ArrayList;

/**
 *
 * @author salva
 */
public class CasillaSorpresa extends Casilla{
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;
    
    CasillaSorpresa(MazoSorpresas mazo, String nombre){
        super(nombre);
        this.mazo = mazo;
    }
    
    @Override
    public String toString(){
        String info = super.toString();
        info += "\n¡Has caído en una sorpresa!";
        info += sorpresa.toString();
        return info;
    }
    
    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(actual, todos)) {
            Sorpresa sorpresa = mazo.siguiente();
            informe(actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
}
