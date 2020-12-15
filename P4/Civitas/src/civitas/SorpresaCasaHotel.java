package civitas;
import java.util.ArrayList;

/**
 *
 * @author abel
 */
public class SorpresaCasaHotel extends Sorpresa {
    
    SorpresaCasaHotel(int valor, String texto){
        init();
        this.valor = valor;
        this.texto = texto;
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        super.aplicarAJugador(actual, todos);
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor * todos.get(actual).cantidadCasasHoteles());
        }
    }
}
