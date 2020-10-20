/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author Salva
 */
public class Jugador {
   //atributos de instancia    
    private String nombre;
    private boolean puedeComprar;
    private float saldo;
    protected boolean encarcelado;
    private int numCasillaActual;

    //atributos de clase
    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected static int HotelesMax = 4;
    protected static float PasoPorSalida = 1000;
    protected static float PrecioLibertad = 200;
    private static float SaldoInicial = 7500;
    
    //atributos de referencia
    ArrayList<TituloPropiedad> propiedades;
}
