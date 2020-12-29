// INICIO EXAMEN
package civitas;

/**
 *
 * @author salva
 */
public class TituloPropiedadHijo extends TituloPropiedad {
    // atributo de instancia que almacena el porcentaje del alquiler que se va a cobrar
    private float porcentaje_alquiler = 0.5f;
    
    TituloPropiedadHijo(String nombre, float alquBase, float factrevalor, float hipbase, float precioComp, float precioEdifi){
        super(nombre, alquBase, factrevalor, hipbase, precioComp, precioEdifi);
    }
    
    // esta clase hace que el propietario reciba la mitad del alquiler pero no hace que el jugador que caiga pague menos
    // he interpretado esta como la mejor opcion para hacerlo
    @Override
    void tramitarAlquiler(Jugador jugador) {
        if (tienePropietario()) {
            if (!esEsteElPropietario(jugador)) {
                float precio = getPrecioAlquiler();
                jugador.paga(precio);
                propietario.recibe(precio*porcentaje_alquiler);
            }
        }
    }
    //la opcion que se podria hacer es
    /*
    @Override
    void tramitarAlquiler(Jugador jugador) {
        super.tramitarAlquiler(jugador);
       propietario.paga(getPrecioAlquiler*porcentaje_alquiler);
    }
    */
    //de esta manera el alquiler es normal, pero el resultado es que el propietario tiene solo la mitad de l alquiler
}

// FIN DE EXAMEN
