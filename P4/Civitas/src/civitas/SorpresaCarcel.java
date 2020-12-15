package civitas;
import java.util.ArrayList;
        
/**
 *
 * @author abel
 */
public class SorpresaCarcel extends Sorpresa{
    
    SorpresaCarcel(Tablero tablero){
        init();
        this.tablero = tablero;
        texto = "Quedas libre de la carcel";
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        super.aplicarAJugador(actual, todos);
        todos.get(actual).encarcelar(tablero.getCarcel());
    }
    
}
