package civitas;
import java.util.ArrayList;
/**
 *
 * @author abel
 */
public class SorpresaPagarCobrar extends Sorpresa {
    
    private float valor;
    
    SorpresaPagarCobrar(int valor, String texto){
        
        this.texto = texto;
        this.valor = valor;       
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }
    
        // INICIO EXAMEN
    @Override 
    public String toString(){
        String info = "Texto: " + super.toString();
        info += "\nValor: " + valor;
        return info;
    }
    // FIN EXAMEN
}
