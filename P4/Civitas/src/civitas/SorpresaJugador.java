package civitas;
import java.util.ArrayList;
/**
 *
 * @author abel
 */
public class SorpresaJugador extends Sorpresa {
    
    SorpresaJugador(int valor, String texto){
        init();
        this.valor = valor;
        this.texto = texto;
    }
    
    // Modificar aplicarAJugador
    
    
//    @Override
//    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
//        super.aplicarAJugador(actual, todos);
//        if (jugadorCorrecto(actual, todos)) {
//            informe(actual, todos);
//            Sorpresa s = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor, "");
//
//            for (int i = 0; i < todos.size(); i++) // o todos.size();
//            {
//                if (i != actual) {
//                    todos.get(i).paga(s.valor);
//                }
//            }
//
//            Sorpresa s1 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor * (todos.size() - 1), "");
//            todos.get(actual).recibe(s1.valor);
//    }
    
}
