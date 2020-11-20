/**
 * @author Salva
 */
package civitas;

/**
 *
 * @author abelrios
 */
public class OperacionInmobiliaria {
    private GestionesInmobiliarias gestionesInmobiliarias;
    int indice;


/*public OperacionInmobiliaria(GestionesInmobiliarias g, int n){
    indice = n;
    
}*/

public OperacionInmobiliaria(){
    indice = 0;
}

GestionesInmobiliarias getGestionesInmobiliarias(){
    return gestionesInmobiliarias;
}

int getIndice(){
    return indice;
}



}

