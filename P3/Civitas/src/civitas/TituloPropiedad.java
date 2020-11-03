/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author Salva
 */
public class TituloPropiedad {
    //atributos de instancia
    private float alquilerBase;
    private static final float factorInteresesHipoteca=1.1f;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    
    //atributo de referencia 
    private Jugador propietario;
    
    //metodos
    
    TituloPropiedad(String nombre, float alquBase, float factrevalor, float hipbase
            , float precioComp, float precioEdifi){
        nombre = nombre;
        alquilerBase = alquBase;
        factorRevalorizacion = factrevalor;
        hipotecaBase = hipbase;
        precioCompra = precioComp;
        precioEdificar = precioEdifi;
        propietario = null;
        numCasas = numHoteles = 0;
        hipotecado = false;
        
    }
    
    @Override
    public String toString(){
        String salida = "Nombre: " + nombre +
                        "\nAlquiler Base: " + alquilerBase + 
                        "\nFactor de Revalorización: " + factorRevalorizacion + 
                        "\nFactor Intereses Hipoteca: " + factorInteresesHipoteca +
                        "\nHipoteca Base: " + hipotecaBase + 
                        "\nHipotecado: " + hipotecado + 
                        "\nNúmero de casas: " + numCasas + 
                        "\nNúmero de Hoteles: " + numHoteles +
                        "\nPrecio de compra: " + precioCompra +
                        "\nPrecio de Edificar: " + precioEdificar;
        return salida;
                        
    }
    
    private float getPrecioAlquiler(){
      //  PrecioAlquiler=AlquilerBase*(1+(NumCasas*0.5)+(NumHoteles*2.5))
      float precio = alquilerBase*(1f+(numCasas*0.5f) + (numHoteles*2.5f));
      if (hipotecado || propietarioEncarcelado())
          precio = 0;
      return precio;
    }
        
    private float getImporteHipoteca(){
        //CantidadRecibida=HipotecaBase*(1+(numCasas*0.5)+(numHoteles*2.5))
        float importe = hipotecaBase*(1+(numCasas*0.5f) + (numHoteles*2.5f));
        return importe; 
    }
    
    float getImporteCancelarHipoteca(){
        float importe = getImporteHipoteca() * factorInteresesHipoteca;
        return importe;
    }
    
    void actualizaPropietarioPorConversion(Jugador jugador){
        propietario = jugador;
    }
    
    boolean cancelarHipoteca(Jugador jugador){
        
    }
    
    int cantidadCasasHoteles(){
        return getNumCasas() + getNumHoteles();
    }
    
    boolean comprar(Jugador jugador){
        
    }
    
    boolean construirCasa(Jugador jugador){
        
    }
    
    boolean construirHotel(Jugador jugador){
        
    }
    
    boolean derruirCasas(int n, Jugador jugador){
        if (esEsteElPropietario(jugador) && getNumCasas() >= n){
            numCasas -= n;
            return true;
        }
        return false;
    }
    
    private boolean esEsteElPropietario(Jugador jugador){
        return jugador == getPropietario();
    }
    
    public boolean getHipotecado(){
        return hipotecado;
    }
    
    
    String getNombre(){
        return nombre;
    }
    
    int getNumCasas(){
        return numCasas;
    }
    
    int getNumHoteles(){
        return numHoteles;
    }
    
    
    float getPrecioCompra(){
        return precioCompra;
    }
    
    float getPrecioEdificar(){
        return precioEdificar;
    }
    
    private float getPrecioVenta(){
        float precio = getPrecioCompra() + getPrecioEdificar()*cantidadCasasHoteles()*factorRevalorizacion;
    }
    
    Jugador getPropietario(){
        return propietario;
    }
    
    boolean hipotecar(Jugador jugador){
        
    }
    
    private boolean propietarioEncarcelado(){
        return getPropietario().isEncarcelado(); 
    }
    
    boolean tienePropietario(){
        return propietario != null;
    }
    
    void tramitarAlquiler(Jugador jugador){
       if (tienePropietario() && !esEsteElPropietario(jugador)){
           jugador.pagaAlquiler(getPrecioAlquiler());
           getPropietario().recibe(getPrecioAlquiler());
       }
           
    }
    
    boolean vender(Jugador jugador){
        if (esEsteElPropietario(jugador) && !hipotecado){
            jugador.recibe(getPrecioVenta());
            propietario = null;
            numCasas= numHoteles = 0;
        }
        else{
            return false;
        }
    }
    
    
}
