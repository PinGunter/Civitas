package civitas;
import java.util.ArrayList;
        
/**
 *
 * @author abel
 */
public class SorpresaCarcel extends Sorpresa{
    
    private Tablero tablero;
    
    SorpresaCarcel(Tablero tablero){
        this.tablero = tablero;
        this.texto = "Vas a la carcel";
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    
    // INICIO EXAMEN
    @Override 
    public String toString(){
        String info = "Texto: " + super.toString();
        info += "\nEsta sorpresa no tiene valor";
        return info;
    }
    // FIN EXAMEN
    
}
