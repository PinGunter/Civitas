/**
 * @author Salva
 */
package civitas;

/**
 *
 * @author abelrios
 */
public class OperacionInmobiliaria {
    private GestionesInmobiliarias gestion;
    private int numPropiedad;


/*public OperacionInmobiliaria(GestionesInmobiliarias g, int n){
    indice = n;
    
}*/

public OperacionInmobiliaria(){
    numPropiedad = 0;
}

public GestionesInmobiliarias getGestionesInmobiliarias(){
    return gestion;
}

public int getIndice(){
    return numPropiedad;
}



}

