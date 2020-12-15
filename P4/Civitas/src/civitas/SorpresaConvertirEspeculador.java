package civitas;
import java.util.ArrayList;
/**
 *
 * @author abel
 */
public class SorpresaConvertirEspeculador extends Sorpresa {
    
    SorpresaConvertirEspeculador(int valor, String texto){
        init();
        this.valor = valor;
        this.texto = texto;
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        super.aplicarAJugador(actual, todos);
        todos.set(actual, new JugadorEspeculador(todos.get(actual), valor));
    }
    
}
